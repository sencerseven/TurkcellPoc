package com.turkcell.poc.service;

import com.turkcell.poc.converter.RecordMapper;
import com.turkcell.poc.model.RecordInputDto;
import com.turkcell.poc.repository.RecordRepository;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

  @Autowired
  private RecordRepository recordRepository;

  @Autowired
  private RecordMapper recordMapper;

  private final BlockingQueue<RecordInputDto> recordQueue = new ArrayBlockingQueue<>(1000);

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  public void addRecord(RecordInputDto recordInputDto) throws InterruptedException {
    if (recordInputDto == null) {
      return;
    }
    this.recordQueue.put(recordInputDto);
  }

  /**
   * This function is used to log incoming requests. It has a multithread structure.
   */
  @PostConstruct
  void startExecutor() {
    this.executorService.submit(() -> {
      while (true) {
        try {
          RecordInputDto inputDto = recordQueue.take();
          this.recordRepository.save(this.recordMapper.recordInputDtoToRecord(inputDto));
        } catch (InterruptedException interruptedException) {
          break;
        } catch (Exception ignored) {
        }
      }
    });
  }


}
