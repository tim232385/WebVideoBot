package tw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.entity.PornRecord;

@Repository
public interface PornRecordDao extends JpaRepository<PornRecord, Long> {

    boolean existsByViewKey(String viewKey);

}