package org.tron.task;

import lombok.extern.slf4j.Slf4j;
import org.tron.check.CheckResultImp;
import org.tron.common.config.Args;
import org.tron.schedule.SendSchedule;

@Slf4j
public class CheckResultTask implements Task {

  private CheckResultImp checkResult;
  private GetAccountTask startOwner;
  private GetAccountTask startTo;
  private GetAccountTask endOwner;
  private GetAccountTask endTo;
  private GetTotalFeeTask total;

  public CheckResultTask(CheckResultImp checkResult, GetAccountTask startOwner,
      GetAccountTask startTo,
      GetAccountTask endOwner, GetAccountTask endTo,
      GetTotalFeeTask total) {
    logger.info("Create task: {}.", getClass().getSimpleName());
    this.checkResult = checkResult;
    this.startOwner = startOwner;
    this.startTo = startTo;
    this.endOwner = endOwner;
    this.endTo = endTo;
    this.total = total;
  }

  @Override
  public void init(Args args) {
    logger.info("Init check result task.");
    checkResult.setStartOwnerAccount(startOwner.getAccountMap());
    checkResult.setStartToAccount(startTo.getAccountMap());
    checkResult.setEndOwnerAccount(endOwner.getAccountMap());
    checkResult.setEndToAccount(endTo.getAccountMap());
    checkResult.setStatistics(SendSchedule.getStatistics());
    checkResult.setTotalFee(total.getTotalFee());
  }

  @Override
  public void start() {
    logger.info("Start check result task.");

    if (checkResult.checkAccount() && checkResult.checkStatistics() && checkResult.checkStorage()) {
      System.out.println("Check result: right!");
    } else {
      System.out.println("Check result: unright!");
    }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown check result task.");
  }
}
