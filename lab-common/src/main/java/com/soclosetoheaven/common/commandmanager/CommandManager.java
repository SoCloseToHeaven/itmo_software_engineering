package com.soclosetoheaven.common.commandmanager;

import com.soclosetoheaven.common.command.AbstractCommand;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.util.LRUCache;

import java.util.Map;

public interface CommandManager<ReturningType, ManagingType> {

    ReturningType manage(ManagingType t) throws ManagingException;

    Map<String, ? extends AbstractCommand> getCommands();

    void addCommand(AbstractCommand command);

    LRUCache<? extends AbstractCommand> getHistory();


}
