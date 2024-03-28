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
}
