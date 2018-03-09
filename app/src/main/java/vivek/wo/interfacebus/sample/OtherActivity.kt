package vivek.wo.interfacebus.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import vivek.wo.interfacebus.BaseSubscribtionInterface
import vivek.wo.interfacebus.InterfaceBus
import vivek.wo.interfacebus.Publish

/**
 * Created by VIVEK-WO on 2018/3/9.
 */
class OtherActivity : AppCompatActivity() {
    val event = "0x0A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
    }

    override fun onResume() {
        super.onResume()
        InterfaceBus.getDefault().register(baseSubscribtionInterface, event, 10)
        InterfaceBus.getDefault().register(baseSubscribtionInterface2, event)
    }

    override fun onPause() {
        super.onPause()
        InterfaceBus.getDefault().unregister(baseSubscribtionInterface)
        InterfaceBus.getDefault().unregister(baseSubscribtionInterface2)
    }

    public fun onXmlClick(v: View) {
        if (v.id == R.id.btn_back) {
            finish();
        } else {
            InterfaceBus.getDefault().post(Publish(event, "onXmlClick"))
        }
    }

    val baseSubscribtionInterface = object : BaseSubscribtionInterface() {
        override fun onSubscribed(publish: Publish?) {
            InterfaceBus.getDefault().cancelEventDelivery(event)//停止往下发布消息
            println("OtherActivity 1 received : ${publish?.event} , ${publish?.`object`}")
        }

    }

    val baseSubscribtionInterface2 = object : BaseSubscribtionInterface() {
        override fun onSubscribed(publish: Publish?) {
            println("OtherActivity 2 received : ${publish?.event} , ${publish?.`object`}")
        }
    }
}