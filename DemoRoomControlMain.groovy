/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
import modules.VaddioCamera
import modules.AMXPrecisSwitcher
import modules.ContemporarySTB
import modules.SamsungDisplay
import modules.BSSAudioArchitect

import groovy.transform.Field

//MU's Global Context
def log = context.log
//Set MU Log level
log.level                   = "INFO"

log.info("ITG Lab Groovy Script")

dvMuseInstance              = context.devices["idevice"]
//Define Required Device names(Should match with the name defined in Muse Device Page)
def demoDvTp                = "AMX-10003"              // Touch Panel (User Interface)
def devDuetVaddioCam        = "dvCamera"               // Vaddio Camera Duet Module Instance (Duet)
def devDuetSamsungDisplay   = "dvSamsungQB75R"         // Samsing Display Duet Module Instance (Duet)
def devAudioArchitect       = "SoundwebLondonBLU-50-2" //Audio Architect Sound Control

//Define Device Instances - Global Variables
@Field dvTpInstance
@Field devDuetVaddioCamInstance
@Field devDuetSamsungDisplayInstance
@Field devAudioArchitectInstance

//Groovy Script Modules
@Field AMXPrecisSwitcher amxPrecis
@Field SamsungDisplay    samsungDisplay
@Field BSSAudioArchitect bssAudioArchitect
@Field ContemporarySTB   contemporaryStb

//Define Button, Channels and Ports
//Source Selects
sourceSelectCHN1        = 1 //Laptop 1
sourceSelectCHN2        = 2 //Laptop 2
sourceSelectCHN3        = 3 //Blu-ray
sourceSelectCHN4        = 4 //Cable TV
sourceSelectCHN5        = 5 //System Off
sourceSelectPort        = 2 //Port for the Source Select Channels

sourceSelectCHNList     = [sourceSelectCHN1, sourceSelectCHN2, sourceSelectCHN3, sourceSelectCHN4, sourceSelectCHN5]

//Privacy Glass Controls
privacyGlassToggleCHN  = 3
privacyGlassTogglePort = 1

privacyGlassCHNList    = [privacyGlassToggleCHN]

volumeUpCHN            = 24  //Momentary - No feedback
volumeDownCHN          = 25  //Momentary - No feedback
speakerMuteCHN         = 26
micMuteCHN             = 100
volumePort             = 5

volumeLevelPort        = 1
volumeLevelCHN         = 2

volumeCHNList          = [volumeUpCHN, volumeDownCHN, speakerMuteCHN, micMuteCHN]

//PTZ Camera Controls
cameraUpCHN            = 45  //Momentary - No feedback
cameraDownCHN          = 46  //Momentary - No feedback
cameraLeftCHN          = 47  //Momentary - No feedback
cameraRightCHN         = 48  //Momentary - No feedback
cameraZoomInCHN        = 116 //Momentary - No feedback
cameraZoomOutCHN       = 117
cameraPreset1CHN       = 21
cameraPreset2CHN       = 22
ptzCameraPort          = 6

cameraCHNList         = [cameraUpCHN, cameraDownCHN, cameraLeftCHN, cameraRightCHN, cameraZoomInCHN, cameraZoomOutCHN,
                            cameraPreset1CHN, cameraPreset2CHN]

//Blue Ray Transport and Menu - Controls
dvdPlayCHN              = 1  //Momentary - No feedback
dvdPauseCHN             = 3  //Momentary - No feedback
dvdStopCHN              = 2  //Momentary - No feedback
dvdFFwdCHN              = 4  //Momentary - No feedback
dvdRRwdCHN              = 5  //Momentary - No feedback
dvdSFWDCHN              = 6  //Momentary - No feedback
dvdSREVCHN              = 7  //Momentary - No feedback
dvdMenuCHN              = 44 //Momentary - No feedback
dvdExitCHN              = 50 //Momentary - No feedback
dvdMenuUpCHN            = 45 //Momentary - No feedback
dvdMenuDownCHN          = 46 //Momentary - No feedback
dvdMenuLeftCHN          = 47 //Momentary - No feedback
dvdMenuRightCHN         = 48 //Momentary - No feedback
dvdMenuSelectCHN        = 49 //Momentary - No feedback
dvdCtrlPort             = 3

dvdCHNList              = [dvdPlayCHN, dvdPauseCHN, dvdStopCHN, dvdFFwdCHN, dvdRRwdCHN, dvdSFWDCHN,dvdSREVCHN,
                          dvdMenuCHN, dvdExitCHN, dvdMenuUpCHN, dvdMenuDownCHN, dvdMenuLeftCHN, dvdMenuRightCHN, dvdMenuSelectCHN]

// STB 
stbChUpCHN              = 22  //Momentary - No feedback
stbChDownCHN            = 23  //Momentary - No feedback
stbPreset1CHN	        = 201 //CNN         //Momentary - No feedback
stbPreset2CHN           = 202 //Fox News    //Momentary - No feedback
stbPreset3CHN           = 203 //MSNBC       //Momentary - No feedback
stbPreset4CHN           = 204 //Bloomberg   //Momentary - No feedback
stbPreset5CHN           = 205 //Weather     //Momentary - No feedback
stbMenuCHN              = 44  //Momentary - No feedback
stbInfoCHN 		        = 101 //Momentary - No feedback
stbGuideCHN 	        = 105 //Momentary - No feedback
stbExitCHN 		        = 50 //Momentary  - No feedback
stbMenuUpCHN            = 45  //Momentary - No feedback
stbMenuDownCHN          = 46  //Momentary - No feedback
stbMenuLeftCHN          = 47  //Momentary - No feedback
stbMenuRightCHN         = 48  //Momentary - No feedback
stbMenuSelectCHN        = 49  //Momentary - No feedback
stbMenuDIGIT_0CHN       = 10  //Momentary - No feedback
stbMenuDIGIT_1CHN       = 11  //Momentary - No feedback
stbMenuDIGIT_2CHN       = 12  //Momentary - No feedback
stbMenuDIGIT_3CHN       = 13  //Momentary - No feedback
stbMenuDIGIT_4CHN       = 14  //Momentary - No feedback
stbMenuDIGIT_5CHN       = 15  //Momentary - No feedback
stbMenuDIGIT_6CHN       = 16  //Momentary - No feedback
stbMenuDIGIT_7CHN       = 17  //Momentary - No feedback
stbMenuDIGIT_8CHN       = 18  //Momentary - No feedback
stbMenuDIGIT_9CHN       = 19  //Momentary - No feedback
stbMenuEnterCHN         = 21  //Momentary - No feedback
stbMenuDashCHN          = 90  //Momentary - No feedback
stbCtrlPort             = 4 

stbCHNList              = [stbChUpCHN,stbChDownCHN,stbPreset1CHN,stbPreset2CHN,stbPreset3CHN,stbPreset4CHN,stbPreset5CHN,stbMenuCHN,
                            stbInfoCHN,stbGuideCHN,stbExitCHN,stbMenuUpCHN,stbMenuDownCHN,stbMenuLeftCHN,stbMenuRightCHN,stbMenuSelectCHN,
                            stbMenuDIGIT_0CHN,stbMenuDIGIT_1CHN,stbMenuDIGIT_2CHN,stbMenuDIGIT_3CHN,stbMenuDIGIT_4CHN,stbMenuDIGIT_5CHN,
                            stbMenuDIGIT_6CHN,stbMenuDIGIT_7CHN,stbMenuDIGIT_8CHN,stbMenuDIGIT_9CHN,stbMenuEnterCHN,stbMenuDashCHN]

dvMuseInstance.online({
    log.info("dvMuseInstance Online")

    //Touch Panel
    //Create dvTP Instance and Add the Watch Events for the above Buttons/Channels defined
    if(context.devices().has(demoDvTp)){
        dvTpInstance = context.devices[demoDvTp]  //Get Device Instance of Touch Panel
        log.info("Touch Panel Available");
    }
    else {
        log.info("Touch Panel Not Available");
    }

    // Hardware Ports
    dvRoomSensor    = dvMuseInstance.io[3]; 
    dvPrivacyGlass  = dvMuseInstance.relay[0]; 
    dvBluRay_IR     = dvMuseInstance.ir[0];
    
    /**
    * Define IP Control Devices
    */

    // This device is controlled by Duet Module.
    if(context.devices().has(devDuetSamsungDisplay)){
        devDuetSamsungDisplayInstance = context.devices[devDuetSamsungDisplay]
        devDuetSamsungDisplayInstance.module.debugState.value = 4
        devDuetSamsungDisplayInstance.module.setInstanceProperty("IP_Address","10.35.88.140")
        //Default Port to connect is 1515, Use the below code to set a different value other than 1515.
        //devDuetSamsungDisplayInstance.module.setInstanceProperty("Port","1515")
        devDuetSamsungDisplayInstance.module.reinitialize()
        samsungDuetModuleComponentIndex = 0
        samsungDisplay = new SamsungDisplay(context, samsungDuetModuleComponentIndex, devDuetSamsungDisplayInstance, dvTpInstance)
    }
    else {
        log.info("Samsung Display Duet Driver Device Instance Unavailable");
    }

    // This device is controlled by direct Socket Connection, No duet module involved.
    if(amxPrecis == null){
        try{
            context.log.info("Initialize Socket Connection..");
            amxPrecis = new AMXPrecisSwitcher(context, dvMuseInstance, dvTpInstance)
            amxPrecis.initConnection("10.35.88.142", 23)
            amxPrecis.numberOfInputs = 4
            amxPrecis.numberOfOutputs = 2
        }
        catch(Exception e){
            context.log.info("Exception in Initialize Socket Connection.."+e);
        }
    }

    /**
    * Define Serial Control Devices
    */

    //This device is controlled by Duet(Serial Connection)
    if(context.devices().has(devDuetVaddioCam)){
        devDuetVaddioCamInstance = context.devices[devDuetVaddioCam]
        devDuetVaddioCamInstance.module.debugState.value = 4
    }
    else {
        log.info("Vaddio Camera Duet Driver Unavailable");
    }

    //This device is controlled by Direct Serial communication, no duet module involved.
    //Contemporary STB connected at Serial Port 0
    stbSerialPortIndex = 0
    dvMuseInstance.serial[stbSerialPortIndex].setCommParams("9600",8,1,"NONE","232");
    contemporaryStb = new ContemporarySTB(context, stbSerialPortIndex, dvMuseInstance, dvTpInstance)

    // BSS Audio Architect
    if(context.devices().has(devAudioArchitect)){
        devAudioArchitectInstance = context.devices[devAudioArchitect]
        bssAudioArchitect = new BSSAudioArchitect(context, devAudioArchitectInstance, dvTpInstance)
    }
    else {
        log.info("Audio Architect Device Unavailable");
    }
    
    dvTpInstance.online({
        log.info("Adding Listeners for the dvTpInstance");
        //Adding Watch Listeners for Source Select Buttons
        for(channel in sourceSelectCHNList){
            dvTpInstance.port[sourceSelectPort].button[channel].watch({
                handleButtonEvent(it)
            })
        }
        
        //Adding Watch Listeners for Privacy Glass Buttons
        for(channel in privacyGlassCHNList){
            dvTpInstance.port[privacyGlassTogglePort].button[channel].watch({
                handleButtonEvent(it)
            })
        }

        //Adding Watch Listeners for volume Buttons
        for(channel in volumeCHNList){
            dvTpInstance.port[volumePort].button[channel].watch({
                handleButtonEvent(it)
            })
        }
        //Adding Watch Listener for Volume Level
        dvTpInstance.port[volumeLevelPort].level[volumeLevelCHN].watch({
            handleLevelEvent(it)
        })

        //Adding Watch Listeners for Camera Buttons
        for(channel in cameraCHNList){
            dvTpInstance.port[ptzCameraPort].button[channel].watch({
                handleButtonEvent(it)
            })
        }

        //Adding Watch Listeners for DVD Buttons
        for(channel in dvdCHNList){
            dvTpInstance.port[dvdCtrlPort].button[channel].watch({
                handleButtonEvent(it)
            })
        }

        //Adding Watch Listeners for STB Buttons
        for(channel in stbCHNList){
            dvTpInstance.port[stbCtrlPort].button[channel].watch({
                handleButtonEvent(it)
            })
        }
    });

    //Timeline Events
    roomOffTimer = context.services.get("timeline")

    roomOffTimer.expired.listen({
        context.log.info("ROOM OFF TIMER RUNNING....");
        if(amxPrecis != null){
            amxPrecis.sendMessage("set switch CI0O1\n")
        }
        if(samsungDisplay != null){
            samsungDisplay.setPower('OFF')
        }
        context.log.info("STOPPING ROOM OFF TIMER....");
        roomOffTimer.stop()
    })

    //Define the listeners on Serial Ports
    dvMuseInstance.serial[0].receive.listen({
        data = new String(it.arguments.get("data"))
        context.log.info("serial[0].receive data: "+data)
        contemporaryStb.handleIncomingMessage(data)
    })

    //Define the listeners on IO Ports
    //IO Sensor Status
    dvRoomSensor.digitalInput.watch({
        context.log.info("Room Sensor Port[0] Old Value: "+it.oldValue)
        context.log.info("Room Sensor Port[0] New Value: "+it.newValue)
        if(it.newValue == false){
            context.log.info("STARTING ROOM OFF TIMER...")
            roomOffTimer.start([10000])
        }
        else {
            context.log.info("STOPPING ROOM OFF TIMER...")
            roomOffTimer.stop()
            if(samsungDisplay != null){
                samsungDisplay.setPower('ON')
                sleep(20)
                samsungDisplay.setSourceSelect('HDMI-HDMI 1')
            }
        }
    })

    //Define the listeners on Relay Ports
    //Relay Status
    dvPrivacyGlass.state.watch({
        context.log.info("Privacy Glass Relay[0] Old Value: "+it.oldValue)
        context.log.info("Privacy Glass Relay[0] New Value: "+it.newValue)
        dvTpInstance.port[privacyGlassTogglePort].channel[privacyGlassToggleCHN].value = it.newValue
    })
});

/**
*Handle Touch panel Button Event
*@param event - event.id
*/
private void handleButtonEvent(event) {
    context.log.info("handleButtonEvent:"+" Event Id:"+event.id+" Event Path:"+event.path+" Event Value:"+event.value);

    String[] pathSplit = event.path.split("/");
    def port   = Integer.parseInt(pathSplit[1])
    def button = Integer.parseInt(pathSplit[3])
    def state  = Boolean.valueOf(event.value)

    context.log.info("Port:"+port);
    context.log.info("Button:"+button);
    context.log.info("State:"+state);

    switch(port){
        case privacyGlassTogglePort:
            context.log.info("Case 1 : "+state);
            if(button == privacyGlassToggleCHN){ //Port:1, Channel:3 is Privacy Glass Relay
                //Toggle
                if(state){
                    context.log.info("Privacy Glass(Relay[0]) State Before Toggle:"+dvPrivacyGlass.state.value);
                    dvPrivacyGlass.state.value = !(dvPrivacyGlass.state.value)
                    context.log.info("Privacy Glass(Relay[0]) State After Toggle:"+dvPrivacyGlass.state.value);
                }
            }
            break;
        case sourceSelectPort:
            context.log.info("Case 2");
            context.log.info("ROOM SENSOR VALUE:"+dvMuseInstance.io[0].digitalInput.value);
            
            if(samsungDisplay != null) {
                // Channel:5 Port:2 is Power OFF
                if(button != sourceSelectCHN5){
                    if(samsungDisplay.powerState != 'ON'){
                        samsungDisplay.setPower('ON')
                        sleep(20)  // Wait for the warming up of the Display
                    }
                    samsungDisplay.setSourceSelect('HDMI-HDMI 1')
                }
                else {
                    samsungDisplay.setPower('OFF')
                }
            }
            
            switch(button){
                case sourceSelectCHN2:
                    dvTpInstance.port[1].send_command("^PGE-Main")
                    dvTpInstance.port[1].send_command("^PPN-[LPS]Laptop1")

                    if(amxPrecis != null){
                        amxPrecis.sendMessage("set switch CI1O1\n")
                    }
                    break;
                case sourceSelectCHN4:
                    dvTpInstance.port[1].send_command("^PGE-Main")
                    dvTpInstance.port[1].send_command("^PPN-[LPS]Laptop2")

                    if(amxPrecis != null){
                        amxPrecis.sendMessage("set switch CI2O1\n")
                    }
                    break;
                case sourceSelectCHN1:
                    dvTpInstance.port[1].send_command("^PGE-Main")
                    dvTpInstance.port[1].send_command("^PPN-[LPS]DVD")
                    //DVD ON
                    dvBluRay_IR.clearAndSendIr(9);

                    if(amxPrecis != null){
                        amxPrecis.sendMessage("set switch CI3O1\n")
                    }
                    break;
                case sourceSelectCHN3:
                    dvTpInstance.port[1].send_command("^PGE-Main")
                    dvTpInstance.port[1].send_command("^PPN-[LPS]Tuner")
                    contemporaryStb.setPower("ON")

                    if( amxPrecis != null){
                        amxPrecis.sendMessage("set switch CI4O1\n")
                    }
                    break;
                case sourceSelectCHN5:
                    dvTpInstance.port[1].send_command("^PPX")
                    dvTpInstance.port[1].send_command("^PGE-Logo")

                    if( amxPrecis != null){
                        amxPrecis.sendMessage("set switch CI0O1\n")
                    }
                    break;
                default:
                    break;
            }
            break;
        case dvdCtrlPort:
            context.log.info("Case 3");
            dvBluRay_IR.clearAndSendIr(button)
            break;
        case stbCtrlPort:
            context.log.info("Case 4");
            contemporaryStb.handleButtonEvent(button, state)
            break;
        case volumePort:
            context.log.info("Case 5");
            try {
                bssAudioArchitect.handleButtonEvent(button, state)
            }
            catch(Exception exec){
                context.log.info("Exception in Calling BSSAudioArchitect"+exec.getLocalizedMessage())
            }
            break;
        case ptzCameraPort: //PTZ Camera Controls
            context.log.info("Case 6");
            if(devDuetVaddioCamInstance == null){
              context.log.info("devDuetVaddioCamInstance is null")
              return;
            }
            camComponentIndex = 0
            VaddioCamera vaddioPtzCamera = new VaddioCamera(context, camComponentIndex, devDuetVaddioCamInstance, dvTpInstance)
            vaddioPtzCamera.handleButtonEvent(button, state)
            break;
        default:
            context.log.info("Default");
            break;
    }
}

/**
*Handle Touch panel Button Event
*@param event - event.id
*/
private void handleLevelEvent(event) {
    context.log.info("handleLevelEvent:"+" Event Id:"+event.id+" Event Path:"+event.path+" Event Value:"+event.value);

    String[] pathSplit = event.path.split("/");
    def port = Integer.parseInt(pathSplit[1])
    def button = Integer.parseInt(pathSplit[3])
    def value = event.value

    context.log.info("Port:"+port);
    context.log.info("Level Channel:"+button);
    context.log.info("Value:"+value);
    //TO-DO
    if(port == 5 && button == 1){
        context.log.info("BSS Audio Architect Volume Level");
        if(bssAudioArchitect != null){
            //Only Feedback so this is not needed
            //bssAudioArchitect.setVolume(value)
        }
    }
}