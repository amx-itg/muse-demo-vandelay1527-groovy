/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package modules
import socket.*

public class AMXPrecisSwitcher implements ISocketReceiver {

    def context
    def port
    def device
    def dvTp
    
    //Use this SocketConnection class for direct TCP connections from Groovy Code
    def SocketConnection socketConn

    def numberOfInputs
    def numberOfOutputs
    def responseVideoInput
    def GET_VID_INP_RES_RES_PATTERN = ~"get (.*) video input ([0-9]+)"

    public AMXPrecisSwitcher(contextInstance, deviceInstance, dvTpinstance) {
        this.context = contextInstance
        this.device = deviceInstance
        this.dvTp = dvTpinstance
    }

    public initConnection(ipAddress, ipPort){
        context.log.info("AMXPrecisSwitcher initConnection("+ipAddress+","+ipPort+")")
        socketConn = new SocketConnection(context, ipAddress, ipPort, this)
    }

    public handleIncomingMessage(responseMessage) {
      context.log.info("AMXPrecisSwitcher handleIncomingMessage("+responseMessage+")")

      def matcher = GET_VID_INP_RES_RES_PATTERN.matcher(responseMessage)

      if(responseMessage != null){
        if(responseMessage.containsIgnoreCase('Welcome to')){
          //Query for the Status of the Video IN
          context.log.info('Query for the Status of the Swither Video Inputs')
          for(input in 1..numberOfInputs){
              sendMessage('get vidin res:'+input+'\n')
          }
        }
        else if(matcher.matches()){
          def signal      = matcher[0][1]
          def inputNumber = Integer.parseInt(matcher[0][2])

          context.log.info('Signal......'+signal)
          context.log.info('InputNumber......'+inputNumber)

          if(signal == 'no video'){
            if(inputNumber == 1){
              context.log.info('Setting channel 102 to false')
              dvTp.port[2].channel[102].value = false
            }
            else if(inputNumber == 2){
              context.log.info('Setting channel 104 to false')
              dvTp.port[2].channel[104].value = false
            }
            else if(inputNumber == 3) {
              context.log.info('Setting channel 101 to false')
              dvTp.port[2].channel[101].value = false
            }
            else if(inputNumber == 4) {
              context.log.info('Setting channel 103 to false')
              dvTp.port[2].channel[103].value = false
            }
          }
          else {
            if(inputNumber == 1){
              context.log.info('Setting channel 102 to true')
              dvTp.port[2].channel[102].value = true
            }
            else if(inputNumber == 2){
              context.log.info('Setting channel 104 to true')
              dvTp.port[2].channel[104].value = true
            }
            else if(inputNumber == 3) {
              context.log.info('Setting channel 101 to true')
              dvTp.port[2].channel[101].value = true
            }
            else if(inputNumber == 4) {
              context.log.info('Setting channel 103 to true')
              dvTp.port[2].channel[103].value = true
            }
          }         
        }
      }
    }

    public sendMessage(message){
        context.log.info("AMXPrecisSwitcher sendMessage("+message+")")
        socketConn.sendMessage(message)
    }
}