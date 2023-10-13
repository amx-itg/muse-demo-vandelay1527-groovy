/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package socket

public class SocketReader implements Runnable {

    def context
    def socket
    def readerStream
    def ISocketReceiver socketReceiver
    def reader
    def response

    public SocketReader(contextInstance, socketInstance, socketReceiverInstance) {
        this.context = contextInstance
        this.socket  = socketInstance
        readerStream = socket.getInputStream()
        reader = new BufferedReader(new InputStreamReader(readerStream))
        socketReceiver = socketReceiverInstance
    }

    void run() {
        context.log.info("SocketReader Thread Running...");
        while((response = reader.readLine())!=null){
            context.log.info("Message Received:"+response)
            socketReceiver.handleIncomingMessage(response)
            context.log.info("handleIncomingMessage Called Successfully")
        }
    }
}