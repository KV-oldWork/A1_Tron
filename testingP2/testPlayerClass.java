DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length, ADDR, PORT);
        try {
        socket.send(packet);
        } catch (IOException e) {
        e.printStackTrace();
        }