/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package socket

public class SocketWriter {

    def context
    def socket
    def writerStream
    

    public SocketWriter(contextInstance, socketInstance) {
        this.context = contextInstance
        this.socket = socketInstance
        writerStream = socket.getOutputStream()
    }
    
    public sendMessage(messageStr){
        context.log.info("SocketWriter sendMessage("+messageStr+")");
        
        context.log.info("SocketWriter Sending Message..."+messageStr);
        writerStream << messageStr
        context.log.info("SocketWriter Sending Message Success..."+messageStr);
    }
}