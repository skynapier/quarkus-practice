package tian.record;

import org.jboss.logging.Logger;
import tian.entity.Record;
import tian.websockets.ChatApplicationLifeCycle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class RecordService {

    @Inject
    EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(RecordService.class);

    public Record persistRecord(@Valid Record record){
        em.persist(record);
        return record;
    }

    @Transactional
    public void initialRecords(){

        String fileName = this.getClass().getResource("/timezone.csv").getFile();
        System.out.println(fileName);
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/timezone.csv")))) {

            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("[,]");
                String id = values[0];
                float lat = Float.parseFloat(values[3]);
                float lng = Float.parseFloat(values[2]);

                Record rec = new Record();
                rec.id = id;
                rec.timeStamp = Long.parseLong(values[1]);
                rec.lat = lat;
                rec.lng = lng;

                em.persist(rec);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Record> findAllRecords() {

        LOGGER.debug("works here");
        return Record.listAll();
    }


    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<Record> findRecordById(String id) {
        return Record.findByIdOptional(id);
    }


    public Record updateRecord(@Valid Record record) {
        Record entity = em.merge(record);
        return entity;
    }


    public void deleteRecord(String id){
        Record.deleteById(id);
    }

}
