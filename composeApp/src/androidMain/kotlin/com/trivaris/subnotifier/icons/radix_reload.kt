package com.trivaris.subnotifier.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Reload: ImageVector
	get() {
		if (_Reload != null) {
			return _Reload!!
		}
		_Reload = ImageVector.Builder(
            name = "com.trivaris.subnotifier.icons.getReload",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.EvenOdd
			) {
				moveTo(1.84998f, 7.49998f)
				curveTo(1.85f, 4.6646f, 4.0598f, 1.85f, 7.5f, 1.85f)
				curveTo(10.2783f, 1.85f, 11.6515f, 3.9064f, 12.2367f, 5f)
				horizontalLineTo(10.5f)
				curveTo(10.2239f, 5f, 10f, 5.2239f, 10f, 5.5f)
				curveTo(10f, 5.7761f, 10.2239f, 6f, 10.5f, 6f)
				horizontalLineTo(13.5f)
				curveTo(13.7761f, 6f, 14f, 5.7761f, 14f, 5.5f)
				verticalLineTo(2.5f)
				curveTo(14f, 2.2239f, 13.7761f, 2f, 13.5f, 2f)
				curveTo(13.2239f, 2f, 13f, 2.2239f, 13f, 2.5f)
				verticalLineTo(4.31318f)
				curveTo(12.2955f, 3.0713f, 10.6659f, 0.85f, 7.5f, 0.85f)
				curveTo(3.4372f, 0.85f, 0.85f, 4.1854f, 0.85f, 7.5f)
				curveTo(0.85f, 10.8146f, 3.4372f, 14.15f, 7.5f, 14.15f)
				curveTo(9.4438f, 14.15f, 11.0622f, 13.3808f, 12.2145f, 12.2084f)
				curveTo(12.8315f, 11.5806f, 13.3133f, 10.839f, 13.6418f, 10.0407f)
				curveTo(13.7469f, 9.7854f, 13.6251f, 9.4931f, 13.3698f, 9.3881f)
				curveTo(13.1144f, 9.283f, 12.8222f, 9.4048f, 12.7171f, 9.6601f)
				curveTo(12.4363f, 10.3425f, 12.0251f, 10.9745f, 11.5013f, 11.5074f)
				curveTo(10.5295f, 12.4963f, 9.165f, 13.15f, 7.5f, 13.15f)
				curveTo(4.0598f, 13.15f, 1.85f, 10.3354f, 1.85f, 7.5f)
				close()
			}
		}.build()
		return _Reload!!
	}

private var _Reload: ImageVector? = null
