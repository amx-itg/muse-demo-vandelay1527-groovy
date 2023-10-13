/**
* Demo Room Groovy code for AMX MUSE Controller.
* @author : Harman ITG Team
*/
package modules

public class SamsungDisplay {

    def context
    def port
    def device
    def dvTp
    def powerState

    public SamsungDisplay() {

    }

    public SamsungDisplay(contextInstance, long portValue, deviceInstance, dvTpinstance) {
        this.context = contextInstance
        this.port = portValue
        this.device = deviceInstance
        this.dvTp = dvTpinstance
        initListeners()
    }

    private initListeners(){
        context.log.info("SamsungDisplay initListeners");

        device.module.commandEvent.listen({
            context.log.info('Listen Trigerred for commandEvent...')
            context.log.info('CommandEvent Port Value: '+it.arguments.get("port"))
            context.log.info('CommandEvent Value: '+it.arguments.get("value"))
        })

        device.module.command(port,"?JAVA_VERSION")

        device.lamp[port].power.watch({
            context.log.info("Display Power Old Value: "+it.oldValue)
            context.log.info("Display Power New Value: "+it.newValue)
            powerState = it.newValue
            if(it.newValue == 'WARMING_UP'){
                context.log.info("Display is Warming up....")
            }
            else if(it.newValue == 'COOLING_DOWN'){
                context.log.info("Display is Cooling down...")
            }
            else if(it.newValue == 'ON'){
                context.log.info("Display is ON")
            }
            else if(it.newValue == 'OFF'){
                context.log.info("Display is OFF")
            }
        })

        device.sourceSelect[port].inputSelect.watch({
            context.log.info("Display Source Select Old Value: "+it.oldValue)
            context.log.info("Display Source Select New Value: "+it.newValue)
        })
    }

    private startTimeLine(){
        context.log.info("SamsungDisplay startTimeLine");

        def powerTimeLine = context.services.get("timeline")
        powerTimeLine.start([3000])
        powerTimeLine.expired.listen({
            context.log.info("powerTimeLine Running");
            //query for Current Power Value
            context.log.info("Current Power Value: "+device.lamp[port].power.value);
        })
    }

    public setPower(powerVal){
        context.log.info("SamsungDisplay setPower("+powerVal+")");
        if(powerVal == 'ON'){
            device.lamp[port].power.value = "ON"
        }
        else if(powerVal == 'OFF'){
            device.lamp[port].power.value = "OFF"
        }
    }

    public setSourceSelect(sourceSelectVal){
        context.log.info("SamsungDisplay setSourceSelect("+sourceSelectVal+")");
        device.sourceSelect[port].inputSelect.value = sourceSelectVal
    }
}