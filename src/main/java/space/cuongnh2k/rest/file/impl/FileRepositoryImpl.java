package space.cuongnh2k.rest.file.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.FileRss;
import space.cuongnh2k.rest.file.query.GetFilePrt;
import space.cuongnh2k.rest.file.query.UpdateFilePrt;

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
    public int uploadFile(List<CreateFilePrt> listPrt) {
        return sqlSession.insert("space.cuongnh2k.rest.file.FileRepository.uploadFile", listPrt);
    }

    @Override
    public int updateFile(UpdateFilePrt prt) {
        return sqlSession.update("space.cuongnh2k.rest.file.FileRepository.updateFile", prt);
    }
}
