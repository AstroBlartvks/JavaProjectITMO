package org.AstroLab.utils.tcpProtocol;

public enum ProtocolStates {
    DISCONNECTED,  // Нет соединения
    CONNECTING,    // Установка соединения
    CONNECTED,     // Соединение активно
    
    SENDING,       // Данные отправляются
    SEND_SUCCESS,  // Успешная отправка
    SEND_FAILED,   // Ошибка отправки

    LISTENING,     // Ожидание входящих данных
    RECEIVING,     // Идёт чтение данных
    RECEIVE_SUCCESS, // Данные успешно получены
    RECEIVE_FAILED,   // Ошибка приёма

    ERROR          // Общая ошибка протокола
}