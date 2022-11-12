package app.application.utils

import ws.schild.jave.Encoder
import ws.schild.jave.MultimediaObject
import ws.schild.jave.encode.AudioAttributes
import ws.schild.jave.encode.EncodingAttributes
import java.io.File

class YoutubeVideoConverter {

    private val encoder = Encoder()
    private val audioAttributes = AudioAttributes()
    private val encodingAttributes = EncodingAttributes()

    init {
        audioAttributes.setCodec("libmp3lame")
        audioAttributes.setBitRate(128000)
        audioAttributes.setChannels(2)
        audioAttributes.setSamplingRate(44100)

        encodingAttributes.setOutputFormat("mp3")
        encodingAttributes.setAudioAttributes(audioAttributes)
    }

    fun convert(source: File){
        encoder.encode(MultimediaObject(source), createTargetFile(source), encodingAttributes)
    }

    private fun createTargetFile(source: File): File {
        val newFileName = source.absolutePath.replace(".mp4", ".mp3")
        return File(newFileName)
    }

}