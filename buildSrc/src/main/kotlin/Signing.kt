import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.util.Properties

object Signing {
    private val keystore_properties = "extras/keystore/template-app.properties"
    private val keystore_file = "extras/keystore/template-app.keystore"

    data class ReleaseValues(
        val storeFilePath: String,
        val storePassword: String,
        val keyAlias: String,
        val keyPassword: String
    )

    lateinit var projectRootDir: String

    fun initialize(project: Project) {
        projectRootDir = project.rootDir.toString()
    }

    fun signingValues(): ReleaseValues {
        val properties = Properties()
        val storePropertiesPath = "$projectRootDir/$keystore_properties"
        val storeFilePath = "$projectRootDir/$keystore_file"
        val keystoreFile = File(storePropertiesPath)
        properties.load(FileInputStream(keystoreFile))
        return ReleaseValues(
            storeFilePath = storeFilePath,
            storePassword = properties.getProperty("keystore.store.password", "MISSING"),
            keyAlias = properties.getProperty("keystore.key.alias", "MISSING"),
            keyPassword = properties.getProperty("keystore.key.password", "MISSING")
        )
    }
}