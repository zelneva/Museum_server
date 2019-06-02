package anastasia.diplom.domain.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping("/api/file")
class UploadFileController {

    @PostMapping
    fun uploadMultipartFile(@RequestBody file: MultipartFile): ResponseEntity<String> {
        val fileName = getNewFileName(file)
        val filePath = writeFile(file, fileName, "/public");
        return ResponseEntity("\"$filePath\"", HttpStatus.OK);
    }


    private fun writeFile(file: MultipartFile, name: String, dir: String): String {
        val filePath = Paths.get(dir, name).toString();
        val fileDest = Paths.get(System.getProperty("user.dir"), filePath)
        Files.write(fileDest, file.bytes)

        return name
    }

    private fun getExt(fileName: String) : String {
        val dotIndex = fileName.lastIndexOf('.') + 1
        return fileName.slice(dotIndex until fileName.length)
    }

    private fun getNewFileName(file: MultipartFile): String {
        val ext = getExt(file.originalFilename)
        val curTime = System.currentTimeMillis().toString()
        return "$curTime.$ext"
    }
}