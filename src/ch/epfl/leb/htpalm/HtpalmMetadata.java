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
   private String metaDataFileName_;
   @Element
   private String configFileName_;
   @Element
   private String baseName_;
   @Element
   private String acqFolder_;
   @Element
   private int nAcq_=0;
   @Element
   private int nFov_=0;
   @ElementList
   private ArrayList<AcqMetadata> acqMetadataList_;
   @ElementList
   private ArrayList<FovMetadata> fovMetadataList_;

   
   
   public HtpalmMetadata(ConfigurationOptions config_){
      acqFolder_ = config_.getFileAcqFolder_();
      baseName_ =config_.getFileBaseName_() ;
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
         if (fovNum == fovMetadataList_.get(ii).getFovNum_()){
            isNewFov = false;
            currentFovIndex = ii;
         }
         ii++;
      }
      if (isNewFov == true){
         currentFovIndex = fovMetadataList_.size();
         FovMetadata fovMetadata = new FovMetadata();
         fovMetadata.setFovNum_(fovNum);
         fovMetadataList_.add(fovMetadata);
      }

      //update the fov metadatalist
      fovMetadataList_.get(currentFovIndex).setnAcq_(fovMetadataList_.get(currentFovIndex).getnAcq_() + 1);
      int currentAcqNum = acqMetadataList_.size();// even though its zero indexed, don't need to subtract 1 because have not added the new acq yet
      fovMetadataList_.get(currentFovIndex).getFovAcqNum_().add(currentAcqNum);
      return currentFovIndex;
   }

   private void updateAcqList(int currentFovIndex, boolean phPreAcquire, boolean phPostAcquire, int[] flCh){
      //update the acquisition list
      AcqMetadata acqMetadata = new AcqMetadata();

      acqMetadata.setPhPreAcquire_(phPreAcquire);
      acqMetadata.setPhPostAcquire_(phPostAcquire);
      acqMetadata.setFlCh_(flCh);
      acqMetadata.setFovNum_(fovMetadataList_.get(currentFovIndex).getFovNum_());
      acqMetadata.setFovAcqNum_(fovMetadataList_.get(currentFovIndex).getnAcq_()-1);//-1 is due to zero indexing
      
      String fovNameStub = baseName_+"_FOV"+Integer.toString(acqMetadata.getFovNum_())+"_Acq"+Integer.toString(acqMetadata.getFovAcqNum_());
      
      if (phPreAcquire){
         acqMetadata.setAcqNamePhPre_(fovNameStub+"_phPre");
      }
      if (phPostAcquire){
         acqMetadata.setAcqNamePhPost_(fovNameStub+"_phPost");
      }
      acqMetadata.setAcqNameFl(new String[flCh.length]);
      for( int jj=0;jj<flCh.length;jj++){
         acqMetadata.getAcqNameFl()[jj] = fovNameStub+"_flCh"+Integer.toString(jj);
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

   /**
    * @return the metaDataFileName_
    */
   public String getMetaDataFileName_() {
      return metaDataFileName_;
   }

   /**
    * @return the configFileName_
    */
   public String getConfigFileName_() {
      return configFileName_;
   }

   /**
    * @return the baseName_
    */
   public String getBaseName_() {
      return baseName_;
   }

   /**
    * @return the acqFolder_
    */
   public String getAcqFolder_() {
      return acqFolder_;
   }

   /**
    * @return the nAcq_
    */
   public int getnAcq_() {
      return nAcq_;
   }

   /**
    * @return the nFov_
    */
   public int getnFov_() {
      return nFov_;
   }

   /**
    * @return the acqMetadataList_
    */
   public ArrayList<AcqMetadata> getAcqMetadataList_() {
      return acqMetadataList_;
   }

   /**
    * @return the fovMetadataList_
    */
   public ArrayList<FovMetadata> getFovMetadataList_() {
      return fovMetadataList_;
   }

   /**
    * @param acqFolder_ the acqFolder_ to set
    */
   public void setAcqFolder_(String acqFolder_) {
      this.acqFolder_ = acqFolder_;
   }

}

