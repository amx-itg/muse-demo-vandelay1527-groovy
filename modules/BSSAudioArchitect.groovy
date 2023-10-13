/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package modules

public class BSSAudioArchitect {

    def context
    def device
    def dvTp

    def volumeUpCHN            = 24  //Momentary - No feedback
    def volumeDownCHN          = 25  //Momentary - No feedback
    def speakerMuteCHN         = 26
    def micMuteCHN             = 100
    def volumePort             = 5

    def volumeLevel            = 2
    def volumeLevelPort        = 1

    def tpMaxVolValue          = 255
    def devMinVol              = -80
    def devMaxVol              = 10

    public BSSAudioArchitect() {

    }

    public BSSAudioArchitect(contextInstance, deviceInstance, dvTpinstance) {
        this.context = contextInstance
        this.device = deviceInstance
        this.dvTp = dvTpinstance
        initListeners()
    }

    private initListeners(){
        context.log.info("BSSAudioArchitect initListeners");
        
        //Mic Mute
        device.Audio["Mic Gain"].Mute.watch({
            context.log.info("Audio Mic Gain Mute Old Value: "+it.oldValue)
            context.log.info("Audio Mic Gain Mute New Value: "+it.newValue)
            if(it.newValue == "Muted"){
                this.dvTp.port[volumePort].channel[micMuteCHN].value = true
            }
            else if(it.newValue == "Unmuted"){
                this.dvTp.port[volumePort].channel[micMuteCHN].value = false
            }
        })

        //Speaker Mute
        device.Audio["Main Volume"]["Override Mute"].watch({
            context.log.info("Audio Main Volume Override Mute Old Value: "+it.oldValue)
            context.log.info("Audio Main Volume Override Mute New Value: "+it.newValue)
            if(it.newValue == "Muted"){
                this.dvTp.port[volumePort].channel[speakerMuteCHN].value = true
            }
            else if(it.newValue == "Unmuted"){
                this.dvTp.port[volumePort].channel[speakerMuteCHN].value = false
            }
        })

        //Volume Level
        device.Audio.NodeRed_Output_Gain.Gain.watch({
            context.log.info("Audio NodeRed Output Gain Old Value: "+it.oldValue)
            context.log.info("Audio NodeRed Output Gain New Value: "+it.newValue)

            def tpValue = tpMaxVolValue*(it.newValue - devMinVol)/(devMaxVol - devMinVol)
            
            context.log.info("Audio NodeRed Output Gain (Volume TP) Value): "+tpValue)

            this.dvTp.port[volumeLevelPort].level[volumeLevel].value = tpValue
        })
    }

    public handleButtonEvent(button, state){
        context.log.info("BSSAudioArchitect handleButtonEvent("+button+','+state+")");
        switch(state){
            case true:
                switch(button){
                    case volumeUpCHN:
                        context.log.info("BSSAudioArchitect Volume UP ON");
                        device.Audio.NodeRed_Output_Gain["Bump Up"].value = "On"
                        break;
                    case volumeDownCHN:
                        context.log.info("BSSAudioArchitect Volume Down ON");
                        device.Audio.NodeRed_Output_Gain["Bump Down"].value = "On"
                        break;
                    case speakerMuteCHN:
                        context.log.info("BSSAudioArchitect Speaker Mute ON");
                        if(device.Audio["Main Volume"]["Override Mute"].value == "Muted"){
                            device.Audio["Main Volume"]["Override Mute"].value = "Unmuted"
                        }
                        else {
                            device.Audio["Main Volume"]["Override Mute"].value = "Muted"
                        }
                        break;
                    case micMuteCHN:
                        context.log.info("BSSAudioArchitect Mic Mute ON");
                        if(device.Audio["Mic Gain"].Mute.value == "Muted"){
                            device.Audio["Mic Gain"].Mute.value = "Unmuted"
                        }
                        else {
                            device.Audio["Mic Gain"].Mute.value = "Muted"
                        }
                        break;
                    default:
                        break;
                }
                break;
            case false:
                switch(button){
                    case volumeUpCHN:
                        context.log.info("BSSAudioArchitect Volume UP Off");
                        device.Audio.NodeRed_Output_Gain["Bump Up"].value = "Off"
                        break;
                    case volumeDownCHN:
                        context.log.info("BSSAudioArchitect Volume DOWN Off");
                        device.Audio.NodeRed_Output_Gain["Bump Down"].value = "Off"
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public setVolume(volumeValue){
        context.log.info("BSSAudioArchitect setVolume("+button+','+state+")");

        def devVal = (volumeValue*(devMaxVol-devMinVol)/tpMaxVolValue) + devMinVol

        device.Audio.NodeRed_Output_Gain.Gain = devVal
    }
}