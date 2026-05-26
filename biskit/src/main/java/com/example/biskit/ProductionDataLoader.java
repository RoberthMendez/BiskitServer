package com.example.biskit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProductionDataLoader {

  private final DataLoader dataLoader;

  public ProductionDataLoader(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  @Async
  @EventListener(ApplicationReadyEvent.class)
  public void cargarDatosDespuesDeArrancar() {
    if (dataLoader.hayDatosBasicosCargados()) {
      return;
    }

    dataLoader.cargarDatosCompletos();
  }
}
