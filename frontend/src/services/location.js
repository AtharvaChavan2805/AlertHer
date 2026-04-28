// Location tracking service
let watchId = null;

export const startLocationTracking = (onLocationChange) => {
  if (!navigator.geolocation) {
    console.error('Geolocation not supported');
    return;
  }

  // Get location immediately
  navigator.geolocation.getCurrentPosition(
    (position) => {
      const { latitude, longitude } = position.coords;
      onLocationChange({ latitude, longitude, timestamp: new Date() });
    },
    (error) => {
      console.error('Error getting location:', error);
    }
  );

  // Set interval for tracking every 10 seconds
  watchId = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        onLocationChange({ latitude, longitude, timestamp: new Date() });
      },
      (error) => {
        console.error('Error getting location:', error);
      }
    );
  }, 10000); // 10 seconds
};

export const stopLocationTracking = () => {
  if (watchId) {
    clearInterval(watchId);
    watchId = null;
  }
};

export const getCurrentLocation = () => {
  return new Promise((resolve, reject) => {
    if (!navigator.geolocation) {
      reject(new Error('Geolocation not supported'));
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        resolve({ latitude, longitude });
      },
      (error) => {
        reject(error);
      }
    );
  });
};
