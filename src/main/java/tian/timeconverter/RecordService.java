package tian.timeconverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class RecordService {
    @Inject
    EntityManager em;


    public Record persistRecord(@Valid Record record){
        Record.persist(record);
        return record;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Record> findAllRecords() {
        return Record.listAll();
    }

}
