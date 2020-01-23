package ru.news.util;

import java.io.File;

public class  GetApplicationPaths {
    public static String getApplicationImagesPath() {
        String folder = System.getProperty("user.dir") + "\\IMG_NEWS_PROJECT\\";
        new File(folder).mkdir();
        return folder;
    }
}
