package com.haishinkit.studio

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.haishinkit.rtmp.RTMPConnection
import com.haishinkit.rtmp.RTMPStream
import com.haishinkit.events.IEventListener
import com.haishinkit.media.Camera
import com.haishinkit.media.Audio
import com.haishinkit.events.Event
import com.haishinkit.util.EventUtils
import com.haishinkit.view.CameraView
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class CameraTabFragment: androidx.fragment.app.Fragment(), IEventListener {
    private var connection: RTMPConnection? = null
    private var stream: RTMPStream? = null
    private var cameraView: CameraView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionCheck = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), 1)
        }
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
        connection = RTMPConnection()
        stream = RTMPStream(connection!!)
        stream?.attachAudio(Audio())
        stream?.attachCamera(Camera(android.hardware.Camera.open()))
        connection?.addEventListener("rtmpStatus", this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_camera, container, false)
        val button = v.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            connection?.connect("rtmp://c1c92d59e7904a78a177ac6026d3b77d-sttvmamprodmediaservice-euwe.channel.media.azure.net:1935/live/34df04c88dd24b3686b6cdd77dd72b3c")
        }
        cameraView = v.findViewById<CameraView>(R.id.camera)
        cameraView?.attachStream(stream!!)
        return v
    }

    override fun handleEvent(event: Event) {
        val data = EventUtils.toMap(event)
        val code = data["code"].toString()
        if (code == RTMPConnection.Code.CONNECT_SUCCESS.rawValue) {
            stream?.publish("live")
        }
    }

    companion object {
        fun newInstance(): CameraTabFragment {
            return CameraTabFragment()
        }
    }
}
