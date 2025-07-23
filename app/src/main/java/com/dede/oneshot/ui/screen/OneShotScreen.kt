@file:OptIn(ExperimentalLayoutApi::class)

package com.dede.oneshot.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastRoundToInt
import com.dede.oneshot.R
import com.dede.oneshot.shot.ALL_ONE_SHOT_LIST
import com.dede.oneshot.shot.OneShot
import com.dede.oneshot.shot.SUGGESTION_ONE_SHOT_LIST
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun OneShotScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val allOneShotList = remember { ALL_ONE_SHOT_LIST }
    var selectedOneShot by remember { mutableStateOf(allOneShotList[0]) }

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

        Column {
            var showPopup by remember { mutableStateOf(false) }

            Text(
                text = buildAnnotatedString {
                    append("用 ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(selectedOneShot.getAppName())
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
                allOneShotList.fastForEachIndexed { i, oneShot ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val iconDraw = oneShot.getAppIcon()
                                if (iconDraw == null) {
                                    Image(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Image(
                                        painter = rememberDrawablePainter(iconDraw),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Text(text = oneShot.getAppName().toString())
                            }
                        },
                        onClick = {
                            selectedOneShot = allOneShotList[i]
                            showPopup = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            SUGGESTION_ONE_SHOT_LIST.fastForEach {
                Text(
                    text = it.getAppName().toString(),
                    style = typography.labelSmall,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            selectedOneShot = it
                        }
                        .border(1.dp, colorScheme.outline, CircleShape)
                        .padding(8.dp, 4.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    OneShot.searchGo(context, selectedOneShot, keyword)
                })
            )
        }
    }
}