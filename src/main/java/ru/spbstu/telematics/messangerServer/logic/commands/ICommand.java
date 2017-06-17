package ru.spbstu.telematics.messangerServer.logic.commands;


import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public interface ICommand {

    /**
     * Реализация паттерна Команда. Метод execute() вызывает соответствующую реализацию,
     * для запуска команды нужна сессия, чтобы можно было сгенерить ответ клиенту и провести валидацию
     * сессии.
     *
     * @param session - текущая сессия
     * @param message - сообщение для обработки
     * @throws CommandException - все исключения перебрасываются как CommandException
     */
    void execute(Session session, Message message) throws CommandException;
}
