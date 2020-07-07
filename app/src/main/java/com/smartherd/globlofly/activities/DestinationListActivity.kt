package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globlofly.service.DestinationService
import com.smartherd.globlofly.service.ServiceBuilder
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

	var country: String?=null

	val countries= arrayOf("India","USA","Australia","Japan")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)

		toolbar.title = title

		val adapter:ArrayAdapter<String?> = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,countries)

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

		spinner1!!.setOnItemSelectedListener(this)

		spinner1.adapter=adapter

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

        // To be replaced by retrofit code
//		destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

		var hashMap = HashMap<String,String>()
//		hashMap.put("country","India")
//		hashMap.put("count","1")

		val destinationList: DestinationService= ServiceBuilder.buildService(DestinationService::class.java)

		val requestCall: Call<List<Destination>> = destinationList.getDestinationList(country)

		requestCall.enqueue(object: Callback<List<Destination>>{
			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {

				Toast.makeText(this@DestinationListActivity,"Error: "+t.toString(),Toast.LENGTH_LONG).show()

			}

			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if (response.isSuccessful){
					val destinationList: List<Destination> = response.body()!!
					destiny_recycler_view.adapter = DestinationAdapter(destinationList)
				}else if(response.code()==401){
					Toast.makeText(this@DestinationListActivity,"Your session has been expired",Toast.LENGTH_LONG).show()
				}else{
					Toast.makeText(this@DestinationListActivity,"Items did not receive",Toast.LENGTH_LONG).show()
				}
			}

		}
		)

    }

	override fun onNothingSelected(parent: AdapterView<*>?) {

	}

	override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
		country=countries[position]
		Toast.makeText(this@DestinationListActivity,"item "+countries[position],Toast.LENGTH_LONG).show()
		loadDestinations()
	}
}
