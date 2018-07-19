package com.reis.game.util

import com.badlogic.gdx.math.Vector2
import com.reis.game.prototypes.AiProto

object AiUtils {
    @JvmStatic
    fun extractWayPoints(data: AiProto.AiData): Array<Vector2> {
        val waypointList = data.waypointList
        val waypoints = ArrayList<Vector2>(waypointList?.size ?: 0)
        if (waypointList != null) {
            for (i in waypointList.indices) {
                val waypoint = waypointList[i]
                waypoints.add(MapUtils.getPositionForTile(waypoint.col, waypoint.row))
            }
        }
        return waypoints.toTypedArray()
    }
}