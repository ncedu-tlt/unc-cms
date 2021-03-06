/*
 * Copyright 2014 ncedu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ncedu.core.data.accessobjects;

import java.util.List;
import ru.ncedu.core.data.entities.Setting;

/**
 *
 * @author daemon
 */
public interface SettingDAO {
    int insert(Setting entity);
    boolean update(Setting entity);
    boolean delete(Setting entity);
    List<Setting> findAll();    
    Setting findByName(String name);
}
