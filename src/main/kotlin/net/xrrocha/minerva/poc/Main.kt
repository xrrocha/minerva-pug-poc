package net.xrrocha.minerva.poc

import de.neuland.pug4j.PugConfiguration
import de.neuland.pug4j.model.PugModel
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main() {

    val stylesheetFileName = "src/main/resources/poc.xsl"

    val outputDirectoryName = "build/output"
    val templateFileName = "src/test/resources/kotlin-goalkicker/01 - Getting started with Kotlin/01 - Hello World.pug"
    val writeFile = writerFor(outputDirectoryName, templateFileName)

    val model = mapOf("name" to "Minerva")
    val documentText =  renderTemplate(templateFileName, model)
    writeFile("xml", documentText)

    val stylesheetText = File(stylesheetFileName).readText()
    val resultString = transformXml(documentText, stylesheetText)
    writeFile("html", resultString)
}

fun transformXml(documentText: String, stylesheetText: String): String {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance().also {
        it.isNamespaceAware = true
    }
    val documentBuilder = documentBuilderFactory.newDocumentBuilder()
    fun parseXml(xmlText: String): Document = documentBuilder.parse(ByteArrayInputStream(xmlText.toByteArray()))

    val contentDocument = parseXml(documentText)
    val contentSource = DOMSource(contentDocument)

    val stylesheetDocument = parseXml(stylesheetText)
    val stylesheetSource = DOMSource(stylesheetDocument)

    val transformerFactory = TransformerFactory.newInstance()
    val transformer = transformerFactory.newTransformer(stylesheetSource)
    val baos = ByteArrayOutputStream()
    val streamResult = StreamResult(baos)
    transformer.transform(contentSource, streamResult)
    return baos.toString()
}

fun renderTemplate(filename: String, model: Map<String, Any>): String {
    val pugModel = PugModel(model)

    val config = PugConfiguration().also {
        it.isPrettyPrint = true
    }
    val template = config.getTemplate(filename)
    return config.renderTemplate(template, pugModel)
}

fun writerFor(directoryName: String, fileName: String): (String, String) -> Unit {
    val outputDir = File(directoryName).also {
        it.mkdirs()
    }
    val baseFileName = baseName(fileName)
    fun writeFile(extension: String, content: String) =
        File(outputDir, "${baseName(baseFileName)}.$extension").writeText(content)
    return ::writeFile
}

fun baseName(fileName: String): String {
    val startPos = fileName.lastIndexOf(File.separatorChar) + 1
    val endPos = fileName.lastIndexOf('.')
    return if (endPos < 0) fileName.substring(startPos)
    else fileName.substring(startPos, endPos)
}
