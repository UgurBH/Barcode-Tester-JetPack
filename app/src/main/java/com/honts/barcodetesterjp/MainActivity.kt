package com.honts.barcodetesterjp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honts.barcodetesterjp.ui.theme.BarcodeTesterTheme
import com.kot.honts.barcodetester.Scanner

class MainActivity : ComponentActivity(), Scanner.ScannerCallBack {

    private val TAG = "Tester-Kot"
    private var scanner: Scanner? = Scanner(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanner!!.initScannerCallBack(this)

        setContent {
            //myUIDesign()
            ScanDataDesign(data = "","")
        }
    }

    override fun onStart() {
        super.onStart()
        scanner!!.createScanner()

    }

    override fun onPause() {
        super.onPause()
        scanner!!.releaseScanner()


    }

    override fun onDestroy() {
        super.onDestroy()
        scanner!!.closeScanner()


    }

    override fun barcodeDecodeSuccess(barcodeData: String, aimID: String) {
        Log.d(TAG, "barcodeDecodeSuccess: recevied barcode data is $barcodeData")
        setContent {
            ScanDataDesign(data = barcodeData, aimID = aimID)
        }

    }


}


//below code is working but openning a new view
@Composable
fun ScanDataDesign(data: String, aimID: String) {
    BarcodeTesterTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Please scan a barcode", modifier = Modifier.padding(20.dp))

                if (!data.isEmpty()) {
                    Text(
                        text = "barcode data is $data \naimID is $aimID", modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp).padding(50.dp),
                        color = MaterialTheme.colors.primaryVariant

                    )
                }

            }
        }
    }
}

@Composable
fun myUIDesign() {

    BarcodeTesterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        )
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var data by remember { mutableStateOf("") }

                Text(text = "Please scan a barcode", modifier = Modifier.padding(20.dp))
                Text(
                    text = data,
                    modifier = Modifier.height(200.dp)
                )

            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BarcodeTesterTheme {
        //myUIDesign()
        ScanDataDesign(data = "", "")
    }
}