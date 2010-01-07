package com.bc.jnn;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;

class ResourceProvider {

    private ResourceProvider() {
    }

    static File getResourceAsFile(String name) {
        final URL url = ResourceProvider.class.getResource(name);
        final URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(MessageFormat.format("Cannot get resource ''{0}'' as file.", name), e);
        }

        return new File(uri);
    }

}
