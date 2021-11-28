package com.example.tp3_star

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.net.URL
import java.net.URLConnection
import java.util.zip.ZipInputStream


class UnzipManager(context: Context, url: String) {
    val context = context
    val passurl = url

    private lateinit var BASE_FOLDER: String

    var count = 0


    /*
     * You can use this flag to check whether Unzippingthread is still running..
     */
    var isDownloadInProgress = false

    /*
     * After unzipping using this flag ,you can check whether any low memory
     * exceptions Occurred or not..and alert user accordingly..
     */
    var isLowOnMemory = false
    var i = 0
    var zipEntry: ZipEntry? = null
    fun startUnzipping(c: Int) {
        count = c
        /*
         * MAKE SURE THAT localContext VARIABLE HAS BEEN INITIALIZED BEFORE
         * INVOKING THIS METHOD.
         *
         * ALSO MAKE SURE YOU HAVE SET "INTERNET" AND "NETWORK ACCESS STATE"
         * PERMISSIONS IN APPLICATION'S MANIFEST FILE.
         */Log.d("DEBUG", "In startUnzipping()")
        BASE_FOLDER = (context.dataDir
                ).toString()
        /*
         *
         */Log.d("DEBUG", "BASE_FOLDER:" + BASE_FOLDER)
        isLowOnMemory = false
        // Start unzipping in a thread..which is safer
        // way to do high cost processes..
        unzipping()
    }

    private fun unzipping()
    {
        System.out.println("------------------------------------------------------------ Before thread  ------------------------------------------------------------")
        Thread {


            System.out.println("------------------------------------------------------------ Starting thread  ------------------------------------------------------------")
            isDownloadInProgress = true
            Log.d("DEBUG", "Unzipping----------------------------")
            val urlConnection: URLConnection
            try {
                /************************************************
                 *
                 * IF you are unzipping a zipped file save under some URL in
                 * remote server
                 */
                val downloadPath = (BASE_FOLDER + File.separator
                + "star_temp" + File.separator)
                val downloadDir = File(BASE_FOLDER + File.separator
                        + "star_temp")

                if(downloadDir.exists())
                {
                    downloadDir.delete()
                }
                downloadDir.mkdirs()

                val finalUrl = URL(
                    passurl /* Url string where the zipped file is stored... */
                )
                urlConnection = finalUrl.openConnection()

                // Get the size of the ( zipped file's) inputstream from
                // server..
                val contentLength: Int = urlConnection.getContentLength()
                Log.d(
                    "DEBUG", "urlConnection.getContentLength():"
                            + contentLength
                )
                /*****************************************************
                 *
                 * YOU CAN GET INPUT STREAM OF A ZIPPED FILE FROM ASSETS FOLDER
                 * AS WELL..,IN THAT CASE JUST PASS THAT INPUTSTEAM OVER
                 * HERE...MAKE SURE YOU HAVE SET STREAM CONTENT LENGTH OF THE
                 * SAME..
                 *
                 */
                val zipInputStream = ZipInputStream(
                    urlConnection.getInputStream()
                )
                /*
                 * Iterate over all the files and folders
                 */zipEntry = zipInputStream.getNextEntry()
                while (zipEntry != null) {
                    Log.d("DEBUG", "Extracting: " + zipEntry!!.getName().toString() + "...")

                    /*
                     * Extracted file will be saved with same file name that in
                     * zipped folder.
                     */
                    val innerFileName = (downloadPath
                            + zipEntry!!.getName())
                    val innerFile = File(innerFileName)

                    /*
                     * Checking for pre-existence of the file and taking
                     * necessary actions
                     */if (innerFile.exists()) {
                        Log.d(
                            "DEBUG",
                            "The Entry already exits!, so deleting.."
                        )
                        innerFile.delete()
                    }

                    /*
                     * Checking for extracted entry for folder type..and taking
                     * necessary actions
                     */if (zipEntry!!.isDirectory()) {
                        Log.d("DEBUG", "The Entry is a directory..")
                        innerFile.mkdirs()
                    } else {
                        Log.d("DEBUG", "The Entry is a file..")
                        val outputStream = FileOutputStream(
                            innerFileName
                        )
                        val BUFFER_SIZE = 2048

                        /*
                         * Get the buffered output stream..
                         */
                        val bufferedOutputStream = BufferedOutputStream(
                            outputStream, BUFFER_SIZE
                        )
                        /*
                         * Write into the file's buffered output stream ,..
                         */
                        var count = 0
                        val buffer = ByteArray(BUFFER_SIZE)
                        while (zipInputStream.read(
                                buffer, 0,
                                BUFFER_SIZE
                            ).also { count = it } != -1
                        ) {
                            bufferedOutputStream.write(buffer, 0, count)
                        }
                        /***********************************************
                         * IF YOU WANT TO TRACK NO OF FILES DOWNLOADED, HAVE A
                         * STATIC COUNTER VARIABLE, INITIALIZE IT IN
                         * startUnzipping() before calling startUnZipping(), AND
                         * INCREMENT THE COUNTER VARIABLE OVER HERE..LATER YOU
                         * CAN USE VALUE OF COUNTER VARIABLE TO CROSS VERIFY
                         * WHETHER ALL ZIPPED FILES PROPERLY UNZIPPED AND SAVED
                         * OR NOT.
                         *
                         * ************************************************
                         */
                        /*
                         * Handle closing of output streams..
                         */
                        bufferedOutputStream.flush()
                        bufferedOutputStream.close()
                    }
                    /*
                     * Finish the current zipEntry
                     */zipInputStream.closeEntry()
                    zipEntry = zipInputStream
                        .getNextEntry()
                }
                /*
                 * Handle closing of input stream...
                 */
                zipInputStream.close()
                Log.d("DEBUG", "--------------------------------")
                Log.d("DEBUG", "Unzipping completed..")
                i = 1
            } catch (e: IOException) {
                Log.d("DEBUG", "Exception occured: " + e.message)
                if (e.message  == "No space left on device") {
                    isLowOnMemory = true
                }
                e.printStackTrace()
            }
            isDownloadInProgress = false
            System.out.println("------------------------------------------------------------ End thread  ------------------------------------------------------------")

        }.start()
    }

}