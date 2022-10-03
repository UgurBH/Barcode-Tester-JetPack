package com.honts.barcodetesterjp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
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
            ScanDataDesign(data = "", "")

        }
    }

    fun clearInput() {
        barcodeDecodeSuccess("", "")
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
        setContent {
            ScanDataDesign(data = barcodeData, aimID = aimID)
            // below is the implement button in onCreate method
            Button(onClick = {
                clearInput()
            }, modifier = Modifier.padding(130.dp, 50.dp))  {
                Text(text = "CLEAR")

            }
        }

    }


}

private val TAG = "Tester-Kot"

//below code is working but openning a new view
@Composable
fun ScanDataDesign(data: String, aimID: String) {

    BarcodeTesterTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Please scan a barcode", modifier = Modifier.padding(20.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    if (!data.isEmpty()) {
                        var scanOutput = "barcode data is $data \naimID is $aimID"

                        Text(
                            //text = "barcode data is $data \naimID is $aimID",
                            text = scanOutput,
                            color = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier.align(Alignment.Center)
                        )

//                        Button(onClick = {
//                            Log.d(TAG, "ScanDataDesign: button clicked")
//                            Log.d(TAG, "ScanDataDesign: entered button if")
//                            //scanOutput = ""
//                            Log.d(TAG, "ScanDataDesign: scanOutputValue is $scanOutput")
//
//                        }, modifier = Modifier.align(Alignment.BottomCenter)) {
//                            Text(text = "CLEAR")
//
//                        }
                    }
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