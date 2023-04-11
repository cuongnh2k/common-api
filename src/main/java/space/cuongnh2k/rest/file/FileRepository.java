package space.cuongnh2k.rest.file;

import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.FileRss;
import space.cuongnh2k.rest.file.query.GetFilePrt;
import space.cuongnh2k.rest.file.query.UpdateFilePrt;

import java.util.List;

public interface FileRepository {

    List<FileRss> getFile(GetFilePrt prt);

    int uploadFile(List<CreateFilePrt> listPrt);

    int updateFile(UpdateFilePrt prt);
}
