/*
 *   * Author: Chris M.
 *   * Author: Mert Kaan K.
 *   * @Author: Michel P.
 *   * @Date: 07.04.2025
 *   *
 *   * @Description: Klasse für Veranstaltungen
 *   *
 *   * @Last Update: 28.05.25, 13:03
 *   * @Reason: Copyright
 *
 *
 */

package com.example.fobidb.repository;

import com.example.fobidb.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * * @Author: Michel P.
 * * @Author: Mert Kaan K.
 * * @Author: Chris M.
 * * @Date: 07.04.2025
 * * @Description: Klasse für Veranstaltungen.
 * <p>
 * * @Last Update: 07.04.2025
 * * @Last Update by: Chris M.
 */

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findTeacherById(Long id);

    Optional<Teacher> findTeacherByEmail(String email);

    boolean existsTeacherByEmail(String email);

    List<Teacher> email(String email);
}
