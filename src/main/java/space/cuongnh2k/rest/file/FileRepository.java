package space.cuongnh2k.rest.file;

import space.cuongnh2k.rest.file.query.CreateFilePrt;

import java.util.List;

public interface FileRepository {

    int uploadFile(List<CreateFilePrt> listPrt);
}
