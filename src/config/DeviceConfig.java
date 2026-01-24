package config;

import java.util.Arrays;

public record DeviceConfig(
		int port,
		String ipAddress,
		String[] neighbors
) {
	@Override
	public String toString() {
		return String.format(
				"%s:%s | neighbors: %s",
				ipAddress, port, Arrays.toString(neighbors)
		);
	}
}
