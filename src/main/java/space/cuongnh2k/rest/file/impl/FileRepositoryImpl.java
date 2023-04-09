package space.cuongnh2k.rest.file.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.query.CreateFilePrt;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final SqlSession sqlSession;

    @Override
    public int uploadFile(List<CreateFilePrt> listPrt) {
        return sqlSession.insert("space.cuongnh2k.rest.file.FileRepository.uploadFile", listPrt);
    }
}
