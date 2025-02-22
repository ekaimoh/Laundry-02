package com.example.morningmobileappmvvm.ui.theme.screens.clients



import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.laundryapp.data.ClientViewModel
import com.example.laundryapp.model.Client
import com.example.laundryapp.navigation.ROUTE_UPDATE_CLIENT


@Composable
fun ViewClientsScreen(navController: NavHostController){
    Column (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        var context = LocalContext.current
        var productRepository = ClientViewModel(navController,context)


        val emptyUploadState = remember { mutableStateOf(Client("","","","","","","")) }
        var emptyUploadsListState = remember { mutableStateListOf<Client>() }

        var clients = productRepository.viewClients(emptyUploadState, emptyUploadsListState)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "All Clients",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                modifier = Modifier.padding(top = 40.dp))


            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(){
                items(clients){
                    ClientItem(
                        imageUrl = it.imageUrl,
                        fullNames = it.fullNames,
                        YourJob = it.YourJob,
                        gender = it.gender,
                        age = it.age,
                        bio = it.bio,
                        id = it.id,
                        navController = navController,
                        productRepository = productRepository
                    )
                }
            }
        }
    }

}

@Composable
fun ClientItem(imageUrl:String,fullNames:String,YourJob:String,gender:String,
               age:String,bio:String,id:String,navController: NavHostController,productRepository:ClientViewModel){
    var showFullText by remember {
        mutableStateOf(false)
    }
    val context= LocalContext.current
    Column (modifier = Modifier.fillMaxWidth(),){
        Card (
            modifier = Modifier
                .padding(10.dp)
                .height(210.dp)
                .animateContentSize(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            )
        ){
            Row (){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Image(modifier = Modifier
                        .width(200.dp)
                        .height(150.dp).padding(10.dp),
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop)
                    Row {
                        Button(
                            onClick = {
                                productRepository.deleteClient(context ,id,navController)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "DELETE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick = {
                            navController.navigate(ROUTE_UPDATE_CLIENT+"/$id")
                        },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = "UPDATE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                    }
                }


                Column(modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 15.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    Text(text = "FULL NAMES",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = fullNames,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "YOUR JOB",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = YourJob,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "GENDER",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = gender,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "AGE",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = age,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "DESCRIPTION",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(modifier = Modifier.clickable {
                        showFullText = !showFullText
                    },
                        text = bio,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (showFullText) 100 else 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }



            }

        }
    }

}