package by.home.aspect.caching;

import by.home.aspect.factories.CacheFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class CacheInitializer {

    public Cache<Object> getCacheFromYamlFile(String file) {

        CacheProperties cacheProperties = null;
        try {
            File yamlFile = getFileFromResource(file);
            cacheProperties = getPropertyFromYamlFile(yamlFile);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return CacheFactory.valueOf(cacheProperties.getCacheType()).getCache(cacheProperties.getCapacity());
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    private CacheProperties getPropertyFromYamlFile(File yamlFile) throws IOException {

        return new ObjectMapper(new YAMLFactory())
                .readValue(yamlFile, CacheProperties.class);
    }

}
