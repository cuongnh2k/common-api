package space.cuongnh2k.rest.file.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.FileRss;
import space.cuongnh2k.rest.file.query.GetFilePrt;
import space.cuongnh2k.rest.file.query.DeleteFilePrt;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final SqlSession sqlSession;

    @Override
    public List<FileRss> getFile(GetFilePrt prt) {
        return sqlSession.selectList("space.cuongnh2k.rest.file.FileRepository.getFile", prt);
    }

    @Override
    public int createFile(List<CreateFilePrt> listPrt) {
        return sqlSession.insert("space.cuongnh2k.rest.file.FileRepository.createFile", listPrt);
    }

    @Override
    public int deleteFile(DeleteFilePrt prt) {
        return sqlSession.update("space.cuongnh2k.rest.file.FileRepository.deleteFile", prt);
    }
}
