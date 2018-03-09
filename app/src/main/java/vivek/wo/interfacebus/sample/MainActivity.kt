package vivek.wo.interfacebus.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import vivek.wo.interfacebus.BaseSubscribtionInterface
import vivek.wo.interfacebus.InterfaceBus
import vivek.wo.interfacebus.Publish

class MainActivity : AppCompatActivity() {
    val event = "0x0A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InterfaceBus.getDefault().register(baseSubscribtionInterface, event)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        InterfaceBus.getDefault().unregister(baseSubscribtionInterface)
    }

    val baseSubscribtionInterface = object : BaseSubscribtionInterface(){
        override fun onSubscribed(publish: Publish?) {
            println("MainActivity received : ${publish?.event} , ${publish?.`object`}")
        }
    }

    public fun onXmlClick(v: View) {
        if (v.id == R.id.btn_intent) {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
        } else {
            InterfaceBus.getDefault().post(Publish(event, "onXmlClick"))
        }
    }


}
