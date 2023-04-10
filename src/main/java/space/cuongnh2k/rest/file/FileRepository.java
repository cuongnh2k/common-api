package space.cuongnh2k.rest.file;

import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.UpdateFilePrt;

import java.util.List;

public interface FileRepository {

    int uploadFile(List<CreateFilePrt> listPrt);

    int updateFile(UpdateFilePrt prt);
}
