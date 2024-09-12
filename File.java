public class ModifyFile {
    private void modifyText() throws IOException {
        File cellConfigFile = FileUtils.getFile(cellConfigResourceDir, CELL_CONFIG_FILE_NAME);
        if (cellConfigFile == null) {
            throw new IOException("Cell Config File is null");
        }

        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(cellConfigFile));
            StringBuilder content = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("regionDetails")) {
                    line = generateCellConfigRegionDetails();
                }
                content.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            writer = new FileWriter(cellConfigFile);
            writer.write(content.toString());
            writer.flush();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            reader.close();
            writer.close();
        }
    }

    private void modifyConfigOrProperty() throws IOException {
        String filePath = cellConfigResourceDir + CELL_CONFIG_FILE_NAME;
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Cannot find file for path: " + filePath);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        properties.set("regionDetails", generateCellConfigRegionDetails());
    }

    // When dealing with clients, file read must happen inside the try-catch block
    // specifically, it should not be split by getting InputStream in try-catch then readAllBytes() outside of try-catch
    private static Collection<? extends Certificate> getCertificateChain(
            String trustCertCollectionFilePath) throws IOException, CertificateException {
        try (InputStream inputStream =
                new ByteArrayInputStream(
                        Files.readAllBytes(Paths.get(trustCertCollectionFilePath)))) {
            final CertificateFactory x509CertFactory = CertificateFactory.getInstance("X.509");
            return x509CertFactory.generateCertificates(inputStream);
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("Unable to convert certificate bytes to X.509", ex);
        }
    }
}
