package com.examen.refactored;

/**
 Representa una notificacion ya construida y valida.
 
  Patron aplicado: Builder.
  Permite construir el objeto de forma legible, valida campos obligatorios
  al ejecutar build() y asigna valores por defecto para campos opcionales.
 */
public class Notification {

    private final String userId;
    private final String message;
    private final String type;
    private final String channel;
    private final String title;
    private final int priority;

    private Notification(Builder builder) {
        this.userId = builder.userId;
        this.message = builder.message;
        this.type = builder.type;
        this.channel = builder.channel;
        this.title = builder.title;
        this.priority = builder.priority;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public static class Builder {

        private String userId;
        private String message;

        // Valores opcionales con defaults
        private String type = NotificationTypes.PLAIN;
        private String channel = NotificationChannels.EMAIL;
        private String title = "Notification";
        private int priority = 1;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder type(String type) {
            if (type != null && !type.trim().isEmpty()) {
                this.type = type.trim();
            }
            return this;
        }

        public Builder channel(String channel) {
            if (channel != null && !channel.trim().isEmpty()) {
                this.channel = channel.trim();
            }
            return this;
        }

        public Builder title(String title) {
            if (title != null && !title.trim().isEmpty()) {
                this.title = title.trim();
            }
            return this;
        }

        public Builder priority(int priority) {
            if (priority > 0) {
                this.priority = priority;
            }
            return this;
        }

        public Notification build() {
            validateRequiredFields();
            return new Notification(this);
        }

        private void validateRequiredFields() {
            if (userId == null || userId.trim().isEmpty()) {
                throw new IllegalArgumentException("User id cannot be empty");
            }

            if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }
        }
    }
}
