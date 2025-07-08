package com.dede.oneshot

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastRoundToInt
import com.dede.oneshot.shot.ALL_ONE_SHOT_LIST
import com.dede.oneshot.ui.theme.OneShotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OneShotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OneShotScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun OneShotScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val oneShotList = remember { ALL_ONE_SHOT_LIST }
    var selectedOneShotIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = object : Arrangement.Vertical {
            override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
                val consumedSize = sizes.fold(0) { a, b -> a + b }
                var current = (totalSize - consumedSize) * 2f / 5f
                sizes.forEachIndexed { index, it ->
                    outPositions[index] = current.fastRoundToInt()
                    current += it.toFloat()
                }
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.padding(bottom = 10.dp),
        ) {
            var showPopup by remember { mutableStateOf(false) }

            Text(
                text = buildAnnotatedString {
                    append("使用 ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(oneShotList[selectedOneShotIndex].getAppName(context))
                    }
                    append(" 搜索")
                },
                modifier = Modifier
                    .clickable {
                        showPopup = true
                    }
            )

            DropdownMenu(
                expanded = showPopup,
                modifier = Modifier.fillMaxHeight(0.8f),
                offset = DpOffset(20.dp, 0.dp),
                onDismissRequest = {
                    showPopup = false
                }) {
                oneShotList.fastForEachIndexed { i, oneShot ->
                    DropdownMenuItem(
                        text = {
                            Text(text = oneShot.getAppName(context).toString())
                        },
                        onClick = {
                            selectedOneShotIndex = i
                            showPopup = false
                        }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var keyword by remember { mutableStateOf("") }

            TextField(
                value = keyword,
                onValueChange = { keyword = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(text = "搜索内容")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = keyword.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            keyword = ""
                        }) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (TextUtils.isEmpty(keyword)) {
                        Toast.makeText(context, "请输入搜索内容", Toast.LENGTH_SHORT).show()
                        return@KeyboardActions
                    }
                    try {
                        oneShotList[selectedOneShotIndex].onSearchGo(context, keyword)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                })
            )
        }
    }
}
