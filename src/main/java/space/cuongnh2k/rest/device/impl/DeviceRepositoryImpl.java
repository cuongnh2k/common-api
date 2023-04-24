package space.cuongnh2k.rest.device.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.query.*;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {
    private final SqlSession sqlSession;

    @Override
    public List<DeviceRss> getDevice(GetDevicePrt prt) {
        return sqlSession.selectList("space.cuongnh2k.rest.device.DeviceRepository.getDevice", prt);
    }

    @Override
    public int createDevice(CreateDevicePrt prt) {
        return sqlSession.insert("space.cuongnh2k.rest.device.DeviceRepository.createDevice", prt);
    }

    @Override
    public int updateDevice(UpdateDevicePrt prt) {
        return sqlSession.update("space.cuongnh2k.rest.device.DeviceRepository.updateDevice", prt);
    }

    @Override
    public int deleteDevice(DeleteDevicePrt prt) {
        return sqlSession.update("space.cuongnh2k.rest.device.DeviceRepository.deleteDevice",prt);
    }
}
