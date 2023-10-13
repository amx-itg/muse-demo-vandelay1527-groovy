/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package modules

public class ContemporarySTB {

    def context
    def port
    def device
    def dvTp
    def stbDeviceId = "1"
    def currentStation = ""

    // STB 
    def stbChUpCHN              = 22  //Momentary - No feedback
    def stbChDownCHN            = 23  //Momentary - No feedback
    def stbPreset1CHN	        = 201 //CNN         //Momentary - No feedback
    def stbPreset2CHN           = 202 //Fox News    //Momentary - No feedback
    def stbPreset3CHN           = 203 //MSNBC       //Momentary - No feedback
    def stbPreset4CHN           = 204 //Bloomberg   //Momentary - No feedback
    def stbPreset5CHN           = 205 //Weather     //Momentary - No feedback
    def stbMenuCHN              = 44  //Momentary - No feedback
    def stbInfoCHN 		        = 101 //Momentary - No feedback
    def stbGuideCHN 	        = 105 //Momentary - No feedback
    def stbExitCHN 		        = 50 //Momentary  - No feedback
    def stbMenuUpCHN            = 45  //Momentary - No feedback
    def stbMenuDownCHN          = 46  //Momentary - No feedback
    def stbMenuLeftCHN          = 47  //Momentary - No feedback
    def stbMenuRightCHN         = 48  //Momentary - No feedback
    def stbMenuSelectCHN        = 49  //Momentary - No feedback
    def stbMenuDIGIT_0CHN       = 10  //Momentary - No feedback
    def stbMenuDIGIT_1CHN       = 11  //Momentary - No feedback
    def stbMenuDIGIT_2CHN       = 12  //Momentary - No feedback
    def stbMenuDIGIT_3CHN       = 13  //Momentary - No feedback
    def stbMenuDIGIT_4CHN       = 14  //Momentary - No feedback
    def stbMenuDIGIT_5CHN       = 15  //Momentary - No feedback
    def stbMenuDIGIT_6CHN       = 16  //Momentary - No feedback
    def stbMenuDIGIT_7CHN       = 17  //Momentary - No feedback
    def stbMenuDIGIT_8CHN       = 18  //Momentary - No feedback
    def stbMenuDIGIT_9CHN       = 19  //Momentary - No feedback
    def stbMenuEnterCHN         = 21  //Momentary - No feedback
    def stbMenuDashCHN          = 90  //Momentary - No feedback
    def stbCtrlPort             = 4 

    def deviceId                = 0
    def commandsMap             = [ (stbChUpCHN): "TU",
									(stbChDownCHN): "TD",
                                    (stbPreset1CHN): "TC=50",
                                    (stbPreset2CHN): "TC=51",
                                    (stbPreset3CHN): "TC=52",
                                    (stbPreset4CHN): "TC=53",
                                    (stbPreset5CHN): "TC=54",
									(stbMenuCHN): "KK=105",
                                    (stbInfoCHN): "KK=100",
                                    (stbGuideCHN): "KK=63",
                                    (stbExitCHN): "KK=111",
									(stbMenuUpCHN): "KK=108",
									(stbMenuDownCHN): "KK=109",
									(stbMenuLeftCHN): "KK=107",
									(stbMenuRightCHN): "KK=106",
									(stbMenuSelectCHN): "KK=110",
									(stbMenuDIGIT_0CHN): "KK=10",
									(stbMenuDIGIT_1CHN): "KK=11",
									(stbMenuDIGIT_2CHN): "KK=12",
									(stbMenuDIGIT_3CHN): "KK=13",
									(stbMenuDIGIT_4CHN): "KK=14",
									(stbMenuDIGIT_5CHN): "KK=15",
									(stbMenuDIGIT_6CHN): "KK=16",
									(stbMenuDIGIT_7CHN): "KK=17",
									(stbMenuDIGIT_8CHN): "KK=18",
									(stbMenuDIGIT_9CHN): "KK=19",
                                    (stbMenuEnterCHN): "KK=110",
									(stbMenuDashCHN): "KK=99" 
                                ]

    public ContemporarySTB() {

    }

    public ContemporarySTB(contextInstance, long portValue, deviceInstance, dvTpinstance) {
        this.context = contextInstance
        this.port = portValue
        this.device = deviceInstance
        this.dvTp = dvTpinstance
    }

    public handleButtonEvent(button, state){
        context.log.info("ContemporarySTB handleButtonEvent("+button+","+state+")");
        if(state){
            sendSerialCommand(commandsMap.get(button))
        }
    }

    public setPower(powerState){
        context.log.info("ContemporarySTB setPower("+powerState+")");
        if(powerState == "ON"){
            sendSerialCommand("P1")
        }
        else {
            sendSerialCommand("P0")
        }
    }

    public handleIncomingMessage(response){
        context.log.info("ContemporarySTB handleIncomingMessage("+response+")");
        if(response.containsIgnoreCase('1TU')){
            def majorChannel = response.substring(4,7)
            def minorChannel = response.substring(11,14)
            context.log.info("Major Channel:"+majorChannel);
            //This code can be used when minorChannel is needed
            //context.log.info("Minor Channel:"+minorChannel);

            if(currentStation != majorChannel){
                currentStation = majorChannel
                //Address:400 holds the Station Value
                //Port 1 is the Port of Address:400 to set Current Station
                def commandToSetChNoTP = '^TXT-'+'400'+','+'0'+','+currentStation
                context.log.info("Set Current Channel Value:"+currentStation);
                dvTp.port[1].send_command(commandToSetChNoTP) 
            }
             
        }
    }

    private sendSerialCommand(command){
        context.log.info("ContemporarySTB sendSerialCommand("+command+")");
        device.serial[port].send(getDeviceSpecificCommand(command));
        //device.serial[port].send(getDeviceSpecificCommand("ST"));
    }

    private def getDeviceSpecificCommand(command){
        return  (">" + stbDeviceId + command + "\r")
    }
}