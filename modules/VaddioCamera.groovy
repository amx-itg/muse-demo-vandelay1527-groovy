/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package modules

public class VaddioCamera {

    def context
    def port
    def device
    def dvTp

    def cameraUpCHN            = 45  //Momentary - No feedback
    def cameraDownCHN          = 46  //Momentary - No feedback
    def cameraLeftCHN          = 47  //Momentary - No feedback
    def cameraRightCHN         = 48  //Momentary - No feedback
    def cameraZoomInCHN        = 116 //Momentary - No feedback
    def cameraZoomOutCHN       = 117 //Momentary - No feedback
    def cameraPreset1CHN       = 21
    def cameraPreset2CHN       = 22
    def ptzCameraPort          = 6

    public VaddioCamera() {

    }

    public VaddioCamera(contextInstance, long portValue, deviceInstance, dvTpinstance) {
        this.context = contextInstance
        this.port = portValue
        this.device = deviceInstance
        this.dvTp = dvTpinstance
        initCameraSpeedValues()
        initListeners()
        startTimeLine()
    }

    private initCameraSpeedValues(){
        context.log.info("VaddioCamera initCameraSpeedValues");
        device.camera[port].zoomSpeed.value = 32
        device.camera[port].panSpeed.value = 32
        device.camera[port].tiltSpeed.value = 32
    }

    private initListeners(){
        context.log.info("VaddioCamera initListeners");

        device.camera[port].preset.watch({
            context.log.info("Cam Preset Old Value: "+it.oldValue)
            context.log.info("Cam Preset New Value: "+it.newValue)
            if(it.newValue == 1){
                context.log.info("Make dvTp CH:21 ON in Port 6")
                this.dvTp.port[ptzCameraPort].channel[cameraPreset1CHN].value = true
                this.dvTp.port[ptzCameraPort].channel[cameraPreset2CHN].value = false
            }
            else if(it.newValue == 2){
                context.log.info("Make dvTp CH:22 ON in Port 6")
                this.dvTp.port[ptzCameraPort].channel[cameraPreset1CHN].value = false
                this.dvTp.port[ptzCameraPort].channel[cameraPreset2CHN].value = true
            }
        })
    }

    private startTimeLine(){
        context.log.info("VaddioCamera startTimeLine");

        //Module Not getting updated with the current preset value from Device so this is not needed.
        /*def presetTimeLine = context.services.get("timeline")
        presetTimeLine.start([3000])
        presetTimeLine.expired.listen({
            context.log.info("presetTimeLine Running");
            //query for Current Preset Value
            context.log.info("Current Preset Value: "+device.camera[port].preset.value);
        })*/
    }

    public handleButtonEvent(button, state){
        context.log.info("VaddioCamera handleButtonEvent("+button+','+state+")");
        switch(state){
            case true:
                switch(button){
                    case cameraUpCHN:
                        device.camera[port].tiltRamp.value = "UP"
                        break;
                    case cameraDownCHN:
                        device.camera[port].tiltRamp.value = "DOWN"
                        break;
                    case cameraLeftCHN:
                        device.camera[port].panRamp.value = "LEFT"
                        break;
                    case cameraRightCHN:
                        device.camera[port].panRamp.value = "RIGHT"
                        break;
                    case cameraZoomInCHN:
                        device.camera[port].zoomRamp.value = "IN"
                        break;
                    case cameraZoomOutCHN:
                        device.camera[port].zoomRamp.value = "OUT"
                        break;
                    case cameraPreset1CHN:
                        device.camera[port].preset.value = 1
                        break;
                    case cameraPreset2CHN:
                        device.camera[port].preset.value = 2
                        break;
                    default:
                        break;
                }
                break;
            case false:
                switch(button){
                    case cameraUpCHN:
                        device.camera[port].tiltRamp.value = "STOP"
                        break;
                    case cameraDownCHN:
                        device.camera[port].tiltRamp.value = "STOP"
                        break;
                    case cameraLeftCHN:
                        device.camera[port].panRamp.value =  "STOP"
                        break;
                    case cameraRightCHN:
                        device.camera[port].panRamp.value =  "STOP"
                        break;
                    case cameraZoomInCHN:
                        device.camera[port].zoomRamp.value = "STOP"
                        break;
                    case cameraZoomOutCHN:
                        device.camera[port].zoomRamp.value = "STOP"
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}