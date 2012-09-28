/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htpalm;

import java.io.File;

/**
 *
 * @author seamus.holden@epfl.ch
 */
public class HtpalmMetadata {
   ConfigurationOptions config_;
   SpiralMosaic mosaic_;
   private String metaDataFileName_;
   private String configFileName_;
   private String fullBaseName_;
   
   public HtpalmMetadata(ConfigurationOptions config_,SpiralMosaic mosaic_){
      this.config_ = config_;
      this.mosaic_ = mosaic_;
      fullBaseName_ = config_.fileAcqFolder_+File.separator+config_.fileBaseName_;
      configFileName_ = fullBaseName_+"_config.xml";
      metaDataFileName_ = fullBaseName_ + "_HtpalmMetadata.xml";

   }
   
   public void saveConfig(){
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
    * @return the fullBaseName_
    */
   public String getFullBaseName() {
      return fullBaseName_;
   }
}
