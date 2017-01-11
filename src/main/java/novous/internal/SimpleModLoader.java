package novous.internal;

import com.google.common.collect.ImmutableList;

import net.minecraft.launchwrapper.LaunchClassLoader;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import novous.api.mod.ModCallback;
import novous.api.mod.ModContainer;
import novous.api.mod.ModLoader;
import novous.api.mod.ModMetadata;

/**
 * A very crude, but effective implementation of {@link ModLoader}.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class SimpleModLoader implements ModLoader {

    private static final Logger LOGGER = LogManager.getLogger("SimpleModLoader");

    private final List<ModContainer> loadedMods = new ArrayList<>();

    private boolean addJarToClasspath(File file) {
        LaunchClassLoader currentLoader = (LaunchClassLoader) Thread.currentThread().getContextClassLoader();
        try {
            currentLoader.addURL(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Class<?>> indexClasses(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            if (entry.getName().endsWith(".class")) {
                String classLoaderName = FilenameUtils.removeExtension(entry.getName()).replace
                        ('/', '.');
                try {
                    classes.add(Class.forName(classLoaderName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new IOException();
                }
            }
        }
        return classes;
    }

    @Override
    public void loadMod(File file) throws IOException {
        LOGGER.info("Indexing: " + file.getName());
        if (!addJarToClasspath(file)) {
            LOGGER.info("Adding jar to classpath failed!");
            return;
        }
        List<Class<?>> classes = indexClasses(file);
        LOGGER.info("Searching for mod class: " + file.getName());
        List<Class<?>> possibleClasses = new ArrayList<>();
        classes.forEach((classType) -> {
            if (ModCallback.class.isAssignableFrom(classType)) {
                if (classType.isAnnotationPresent(ModMetadata.class)) {
                    LOGGER.info("Found mod class: " + classType.getName());
                    possibleClasses.add(classType);
                }
            }
        });
        if (possibleClasses.size() == 0) {
            LOGGER.info("No mod classes found! Aborting load on " + file.getName());
            return;
        }
        Class<?> targetClass;
        if (possibleClasses.size() > 1) {
            LOGGER.info("More than 1 mod classes have been found!");
            LOGGER.info("Taking " + possibleClasses.get(0).getName() + " as mod class!");
        }
        targetClass = possibleClasses.get(0);
        ModCallback callback = newCallback(targetClass);
        if (callback == null) {
            LOGGER.info("Failed to create new callback!");
            return;
        }
        ModMetadata modMetadata = targetClass.getAnnotation(ModMetadata.class);
        ModContainer container = new ModContainer(callback, modMetadata);
        this.loadedMods.add(container);
        LOGGER.info("Mod (" + file.getName() + ") loaded!");
    }

    private ModCallback newCallback(Class<?> clazz) {
        try {
            return (ModCallback) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ImmutableList<ModContainer> getLoadedMods() {
        return ImmutableList.copyOf(loadedMods);
    }


}
