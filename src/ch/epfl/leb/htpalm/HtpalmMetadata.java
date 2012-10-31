/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.epfl.leb.htpalm;

import java.io.File;
import java.util.ArrayList;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;
import static ch.epfl.leb.htpalm.Debug.DEBUG;

/**
 *
 * @author seamus.holden@epfl.ch
 */
@Root
public class HtpalmMetadata {

   @Element
   String metaDataFileName_;
   @Element
   String configFileName_;
   @Element
   String baseName_;
   @Element
   String acqFolder_;
   @Element
   int nAcq_=0;
   @Element
   int nFov_=0;
   @ElementList
   ArrayList<AcqMetadata> acqMetadataList_;
   @ElementList
   ArrayList<FovMetadata> fovMetadataList_;

   
   
   public HtpalmMetadata(ConfigurationOptions config_,SpiralMosaic mosaic_){
      acqFolder_ = config_.fileAcqFolder_;
      baseName_ =config_.fileBaseName_ ;
      configFileName_ = baseName_+"_config.xml";
      metaDataFileName_ = baseName_+ "_htpalmMetadata.xml";
      fovMetadataList_ = new ArrayList<FovMetadata>();
      acqMetadataList_ = new ArrayList<AcqMetadata>();
      nAcq_=0;
      nFov_=0;
   }
   
   public void saveMetadata(){
      String fpath = acqFolder_+File.separator+metaDataFileName_;
      File f = new File(fpath);
      Serializer serializer = new Persister();
      try {
         serializer.write(this,f);
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   public void addNewAcquisition(int fovNum, boolean phPreAcquire, boolean phPostAcquire, int[] flCh){
      nAcq_++;
      int currentFovIndex = updateFovList(fovNum);
      updateAcqList(currentFovIndex,phPreAcquire, phPostAcquire, flCh);
      
   }
   
   private int updateFovList(int fovNum){
      
      //find out if this fov has been imaged before
      boolean isNewFov = true;
      Integer currentFovIndex=null;
      int ii = 0;
      while (isNewFov == true && ii<fovMetadataList_.size()){
         //if imaged before, implement the FOV count
         if (fovNum == fovMetadataList_.get(ii).fovNum_){
            isNewFov = false;
            currentFovIndex = ii;
         }
         ii++;
      }
      if (isNewFov == true){
         currentFovIndex = fovMetadataList_.size();
         FovMetadata fovMetadata = new FovMetadata();
         fovMetadata.fovNum_ = fovNum;
         fovMetadataList_.add(fovMetadata);
      }

      //update the fov metadatalist
      fovMetadataList_.get(currentFovIndex).nFovAcq_++;
      int currentAcqNum = acqMetadataList_.size();// even though its zero indexed, don't need to subtract 1 because have not added the new acq yet
      fovMetadataList_.get(currentFovIndex).fovAcqNum_.add(currentAcqNum);
      return currentFovIndex;
   }

   private void updateAcqList(int currentFovIndex, boolean phPreAcquire, boolean phPostAcquire, int[] flCh){
      //update the acquisition list
      AcqMetadata acqMetadata = new AcqMetadata();

      acqMetadata.phPreAcquire_ = phPreAcquire;
      acqMetadata.phPostAcquire_ = phPostAcquire;
      acqMetadata.flCh_ = flCh;
      acqMetadata.fovNum_ = fovMetadataList_.get(currentFovIndex).fovNum_;
      acqMetadata.fovAcqNum_ =fovMetadataList_.get(currentFovIndex).nFovAcq_-1;//-1 is due to zero indexing
      
      String fovNameStub = baseName_+"_FOV"+Integer.toString(acqMetadata.fovNum_)+"_Acq"+Integer.toString(acqMetadata.fovAcqNum_);
      
      if (phPreAcquire){
         acqMetadata.acqNamePhPre_ = fovNameStub+"_phPre";
      }
      if (phPostAcquire){
         acqMetadata.acqNamePhPost_ = fovNameStub+"_phPost";
      }
      acqMetadata.acqNameFl = new String[flCh.length];
      for( int jj=0;jj<flCh.length;jj++){
         acqMetadata.acqNameFl[jj] = fovNameStub+"_flCh"+Integer.toString(jj);
      }

      acqMetadataList_.add(acqMetadata);
   }
   
   /**
    * @return the metaDataFileName_
    */
   public String getMetaDataFileName() {
      return metaDataFileName_;
   }

   /**
    * @return the configFileName_
    */
   public String getConfigFileName() {
      return configFileName_;
   }

}

