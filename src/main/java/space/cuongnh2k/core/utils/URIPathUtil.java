package space.cuongnh2k.core.utils;

public class URIPathUtil {

    public static String contact (final String ...paths) {
        StringBuilder pathBuilder = new StringBuilder();

        pathBuilder.append("/");

        if (paths.length == 0) {
            return pathBuilder.toString();
        }

        for (String path : paths) {
            if(path.startsWith("/")) {
                pathBuilder.append(path.substring(1));
            } else {
                pathBuilder.append(path);
            }

            if(!path.endsWith("/")) {
                pathBuilder.append("/");
            }
        }

        return pathBuilder.toString();
    }
}
