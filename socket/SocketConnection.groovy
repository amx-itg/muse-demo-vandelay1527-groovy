/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package socket
import socket.*

public class SocketConnection {

    def context
    def ipAddress
    def port
    def socket
    def socketReader
    def socketWriter
    def ISocketReceiver socketReceiver

    public SocketConnection(contextInstance, ipAddressValue, portValue, socketReceiverInstance) {
        this.context = contextInstance
        this.ipAddress = ipAddressValue
        this.port = portValue
        this.socketReceiver = socketReceiverInstance
        initConnection()
    }
    
    public initConnection(){
        context.log.info("SocketConnection initConnection");
        context.log.info("IP Address:"+ipAddress);
        context.log.info("Port"+port);
        socket = new Socket(ipAddress, port)
        socketWriter = new SocketWriter(context, socket)
        socketReader = new SocketReader(context, socket, socketReceiver)
        new Thread(socketReader).start()
    }

    public sendMessage(message){
        context.log.info("SocketConnection sendMessage("+message+")");
        socketWriter.sendMessage(message)
        sleep(200)
    }
}